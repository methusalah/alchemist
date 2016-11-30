package com.brainless.alchemist.view.tab.report;

import com.brainless.alchemist.view.control.OverviewTab;

public class ReportTab extends OverviewTab {

	public ReportTab() {
		setText("Report");
		setContent(new Report());
	}
}
