package model.ECS.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.simsilica.es.EntityChange;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;

/**
 * An extended EntityData able to give snapshot states (memento) and reset to a previously created state.
 * @author Benoît
 *
 */
public class SavableEntityData extends DefaultEntityData implements Cloneable{
	public Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> entities = new HashMap<>();
	
	@Override
	protected void entityChange(EntityChange change) {
		super.entityChange(change);
		
		if(!entities.containsKey(change.getEntityId()))
			entities.put(change.getEntityId(), new HashMap<>());
		
		Map<Class<? extends EntityComponent>, EntityComponent> components = entities.get(change.getEntityId());
		if(change.getComponent() == null)
			components.remove(change.getComponentType());
		else
			components.put(change.getComponentType(), change.getComponent());
	}

	/**
	 * Create a new immutable state from that point.
	 * With this method, components are assumed immutable too, to avoid time consuming cloning.
	 * For mutable components, use createClonedMemento() method.
	 * @return the entity data state
	 */
	public EntityDataMemento createMemento(){
		Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> res = new HashMap<>(entities);
		for(EntityId eid : res.keySet())
			res.put(eid, Collections.unmodifiableMap(new HashMap<>(res.get(eid))));
		
		return new EntityDataMemento(new HashMap<>(res), this);
	}

	/**
	 * Create a new immutable state from that point.
	 * This method is made to save component by clone, in the case component are not immutable.
	 * Component must override the Object.clone() method with a public visibility.
	 * @return the entity data state
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public EntityDataMemento createClonedMemento() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method clone = Object.class.getMethod("clone");
		Map<EntityId, Map<Class<? extends EntityComponent>, EntityComponent>> res = new HashMap<>(entities);
		for(EntityId eid : res.keySet()){
			Map<Class<? extends EntityComponent>, EntityComponent> comps = new HashMap<>(res.get(eid));
			for(Class<? extends EntityComponent> compClass : comps.keySet())
				comps.put(compClass, (EntityComponent)(clone.invoke(comps.get(compClass))));
			res.put(eid, Collections.unmodifiableMap(new HashMap<>(comps)));
		}
		
		return new EntityDataMemento(new HashMap<>(res), this);
	}
	
	/**
	 * Reset to a previous state. 
	 * @param memento
	 */
	public void setMemento(EntityDataMemento memento){
		if(memento.getOriginator() != this)
			throw new IllegalArgumentException(SavableEntityData.class.getSimpleName() + " only accept memento it has created himself.");
		// we clean the entity data up to the last created entity ID
		long l = createEntity().getId();
		for(long i = 0; i <= l; i++)
			removeEntity(new EntityId(i));
		
		// then we set all components stored in the memento
		for(EntityId eid : memento.getState().keySet()){
			Map<Class<? extends EntityComponent>, EntityComponent> components = memento.getState().get(eid);
			for(EntityComponent comp : components.values())
				setComponent(eid, comp);
		}
	}
}
