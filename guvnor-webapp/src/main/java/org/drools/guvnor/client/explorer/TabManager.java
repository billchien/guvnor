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

package org.drools.guvnor.client.explorer;


import org.drools.guvnor.client.ruleeditor.MultiViewRow;

import java.util.List;

public interface TabManager {

    void openAssetsToMultiView(MultiViewRow[] rows);

    void openCategory(String categoryName, String categoryPath);

    void openPackageViewAssets(String uuid, String name, String key, List<String> strings, Boolean aBoolean, String text);

    boolean showIfOpen(String id);

    void openInboxIncomingPagedTable(String title);

    void openInboxPagedTable(String title);

    void openStatePagedTable(String stateName);
}
