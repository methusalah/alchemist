package presentation.report;

import presentation.control.OverviewTab;

public class ReportTab extends OverviewTab {

	public ReportTab() {
		setText("Report");
		setContent(new Report());
	}
}
