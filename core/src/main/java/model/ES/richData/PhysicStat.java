package model.ES.richData;

public class PhysicStat {
	public final CollisionShape shape;
	public final double restitution;
	public final double mass;
	
	public PhysicStat(double mass, CollisionShape shape, double restitution) {
		this.mass = mass;
		this.shape = shape;
		this.restitution = restitution;
	}
}
