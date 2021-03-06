/*
 * Copyright (c) 2014, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jcstress.util;

import java.io.*;

public class FileUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static String copyFileToTemp(String cpLocation, String prefix, String suffix) throws IOException {
        try (InputStream is = FileUtils.class.getResourceAsStream(cpLocation)){
            if (is == null) {
                throw new IOException("Resource not found: " + cpLocation);
            }
            File file = File.createTempFile(prefix, suffix);
            file.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(file);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int read;
                while ((read = is.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
                    bos.write(buffer, 0, read);
                }
                return file.getAbsolutePath();
            }
        }
    }

}
