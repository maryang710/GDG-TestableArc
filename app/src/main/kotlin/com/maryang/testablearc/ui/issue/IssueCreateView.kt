package com.maryang.testablearc.ui.issue

import com.maryang.testablearc.base.BaseView

interface IssueCreateView : BaseView {
    fun onIssueCreated()
    fun onIssueCreateFail()
}
