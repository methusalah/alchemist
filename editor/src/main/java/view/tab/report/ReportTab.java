package main.java.view.tab.report;

import main.java.view.control.OverviewTab;

public class ReportTab extends OverviewTab {

	public ReportTab() {
		setText("Report");
		setContent(new Report());
	}
}
