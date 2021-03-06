/*
 * Copyright 2011 JBoss Inc
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.drools.guvnor.client.explorer.navigation.browse;

import com.google.gwt.user.client.ui.IsTreeItem;
import com.google.gwt.user.client.ui.IsWidget;
import org.drools.guvnor.client.explorer.TabType;

import java.util.Collection;
import java.util.List;


public interface BrowseTreeView extends IsWidget {
    interface Presenter {

        void onTreeItemSelection(IsTreeItem selectedItem, String title);

        void onTreeItemOpen(IsTreeItem target);

    }

    IsTreeItem addRootTreeItem();

    IsTreeItem addInboxIncomingTreeItem();

    Collection<IsTreeItem> getChildren(IsTreeItem openedItem);

    IsTreeItem addInboxRecentEditedTreeItem();

    IsTreeItem addInboxRecentViewedTreeItem();

    IsTreeItem addFind();

    IsTreeItem addRootStateTreeItem();

    IsTreeItem addRootCategoryTreeItem();

    IsTreeItem addTreeItem(IsTreeItem rootTreeItem, String one);

    void showMenu(RulesNewMenu rulesNewMenu);

    void setPresenter(Presenter presenter);

    void removeStates();

    IsTreeItem addStateItem(String state);

    void removeCategories(IsTreeItem treeItem);
}
