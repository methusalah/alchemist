package view.tab.report;

import view.control.OverviewTab;

public class ReportTab extends OverviewTab {

	public ReportTab() {
		setText("Report");
		setContent(new Report());
	}
}
