package presentation.report;

import presentation.commonControl.OverviewTab;

public class ReportTab extends OverviewTab {

	public ReportTab() {
		setText("Report");
		setContent(new Report());
	}
}
