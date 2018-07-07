/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package org.qoan.gui.components.workspace.resource;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

public class FileUploader extends Panel {

    private static String fileUploaderName = "File Uploader";

    public FileUploader() {
        super(fileUploaderName);
        initialize();
    }

    private void initialize() {

        Upload upload = new Upload();
        //upload.addSucceededListener(event -> ; );
    }

}
