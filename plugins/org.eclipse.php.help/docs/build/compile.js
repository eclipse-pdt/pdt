/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
// JDK >= 8 support:
var version = java.lang.System.getProperty("java.version").split(".");
var encoding = "UTF-8";

var javaImporter = new JavaImporter(Packages.org.apache.tools.ant.types, java.io.File, java.util.Scanner, java.lang.Exception);
with (javaImporter) {
    var debug = function(el) {
        var echo = project.createTask('echo');
        echo.setMessage(el);
        echo.perform();
    }, enableConvert = project.getProperty('run.convert').toLowerCase() == 'true', tree = {
         sub : [],
         contexts : []
    }, globalTree = tree, sourceDir = new File(project.getProperty('doc.source.dir')), destinationDir = new File(project.getProperty('doc.build.dir')), templateDir = new File(project.getProperty('doc.template.dir')), nameReg = /^[0-9]{3}-(.*?)\.md$/i, baseNameReg = /^[0-9]{3}-(.*?)$/i, convertToHtml = function(file, target) {
        // Converting may be disabled
    }, footerTemplateRead = function() {
        var scanner = new Scanner(new File(templateDir, "footer.html"), encoding);
        
        return scanner.useDelimiter("\\A").next().trim().replace('${project.version}', project.getProperty('project.version').replace('SNAPSHOT', project.getProperty('buildQualifier')));
    }, footerTemplate = footerTemplateRead(), headerTemplateRead = function() {
        var scanner = new Scanner(new File(templateDir, "header.html"), encoding); 
        
        return scanner.useDelimiter("\\A").next().trim();
        
    }, headerTemplate = headerTemplateRead(), readAndConvert = function(tree, file, destination) {
        var scanner = new Scanner(file, encoding), content = scanner.useDelimiter("\\A").next().trim(), lines = content.split("\n");
        if (content.substring(0, 2) != '# ') {
            debug(file.getAbsolutePath());
            throw new Exception("Title is required! : " + file.getAbsolutePath());
        }
        tree.title = lines[0].substring(2).trim();
        tree.url = destination.getAbsolutePath().substring(destinationDir.getAbsolutePath().length()+1);
        tree.url = tree.url.substr(0, tree.url.length()-3) + '.html';
        
        tree.hasToc = content.indexOf('<!--toc-->') != -1;
        tree.hasMainToc = content.indexOf('<!--main-toc-->') != -1;
        tree.contexts = [];
        
        // TODO: read with right title + ID position
        for (var i = 0; i < lines.length; i++) {
            var lastPos = 0;
            while (lastPos < lines[i].length() && (lastPos = lines[i].indexOf('<!--context:', lastPos)) != -1) {
                var to = lines[i].indexOf('-->', lastPos + 5);
                if (to == -1) {
                    lastPos += 5;
                } else {
                    var el = lines[i].substring(lastPos + 12, to);
                    tree.contexts.push(el);
                    lastPos = to + 1;
                }
            }
        }
        tree.realTarget = new File(destination.getParent(), destination.getName().substring(0, destination.getName().length() - 3) + '.html');
        if (enableConvert && (!tree.realTarget.exists() || tree.realTarget.lastModified() != file.lastModified())) {
            tree.convert = true;
        } else {
            tree.convert = false;
        }
    }, analyze = function(tree, file, destination) {
        
        tree.source = file;
        tree.target = destination;
        if (file.isDirectory()) {
            var mk = project.createTask('mkdir');
            mk.setDir(destination);
            mk.perform();
            var keys = [];
            var list = {};
            var sorted = [];
            for (var i = 0, files = file.listFiles(); i < files.length; i++) {
                keys.push(files[i].getName());
                list[files[i].getName()] = files[i]
            }
            keys.sort();
            for (var i in keys) {
                sorted[keys[i]] = list[keys[i]];
            }
            for (var i in sorted) {
                var f = sorted[i];
                var exec = baseNameReg.exec(f.getName());
                if (exec != null) {
                    if (exec[1] == 'index.md') {
                        tree.name = exec[1];
                        analyze(tree, f, new File(destination, exec[1]));
                    } else {
                        var sub = {
                             sub : []
                        };
                        analyze(sub, f, new File(destination, exec[1]));
                        tree.sub.push(sub);
                    }
                } else {
                    analyze({}, f, new File(destination, f.getName()));
                }
            }
        } else {
            if (!baseNameReg.test(file.getName())) {
                var t = project.createTask('copy');
                t.setFile(file);
                t.setTofile(destination);
                t.perform();
                destination.setLastModified(file.lastModified());
                return;
            }
            readAndConvert(tree, file, destination);
        }
    }, buildToc = function(tree, prefix) {
        var res = '';
        if (tree.sub.length > 0) {
            res += '<ul>';
            for (var i in tree.sub) {
                var el = tree.sub[i];
                res += '<li><a href="' + prefix + el.url + '">' + el.title + '</a>';
                res += buildToc(el, prefix);
                res += '</li>';
            }
            res += '</ul>';
            
            return res;
        } else {
            return '';
        }
    }, converted = 0, process = function(tree) {
        if (tree.convert) {
            var task = project.createTask('copy');
            task.setFile(tree.source);
            task.setTofile(tree.target);
            task.perform();
            
            task = project.createTask('wikitext-to-html');
            task.setMarkupLanguage("Markdown");
            task.setFile(tree.target);
            task.setTitle(tree.title +  " - PHP Development Tools Help");
            task.setOverwrite(true);
            task.setXhtmlStrict(true);
            task.setValidate(false);
            var style = new org.eclipse.mylyn.wikitext.ant.internal.MarkupToHtmlTask.Stylesheet();
            var pre = '';
            for (var m = 0; m < tree.url.split('/').length - 1; m++) {
                pre += '../';
            }
            style.setUrl(pre + "css/main.css");
            task.addStylesheet(style);
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken("<body>");
            task.setValue("<body>" + headerTemplate);
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('</body>');
            task.setValue(footerTemplate.replace('${file_path}', tree.url) + "</body>");
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--ref-start-->');
            task.setValue('<div class="ref">');
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--ref-end-->');
            task.setValue('</div>');
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--links-end-->');
            task.setValue('</div>');
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--links-start-->');
            task.setValue('<div class="links">');
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--note-end-->');
            task.setValue('</div>');
            task.perform();
            
            task = project.createTask('replace');
            task.setFile(tree.realTarget);
            task.setToken('<!--note-start-->');
            task.setValue('<div class="note">');
            task.perform();
            
            // fixes
            // images:
            task = project.createTask('replaceregexp');
            task.setFile(tree.realTarget);
            task.setFlags('gs');
            task.setMatch('!\\[([^\\]]*?)\\]\\(([^\\)]*?) "([^"]*?)"\\)');
            task.setReplace('<img src="\\2" alt="\\1" title="\\3" />');
            task.perform();
            
            task = project.createTask('replaceregexp');
            task.setFile(tree.realTarget);
            task.setFlags('gs');
            task.setMatch('!\\[([^\\]]*?)\\]\\(([^\\)]*?)\\)');
            task.setReplace('<img src="\\2" alt="\\1" />');
            task.perform();
            
            // links
            task = project.createTask('replaceregexp');
            task.setFile(tree.realTarget);
            task.setFlags('gs');
            task.setMatch('\\[([^\\]]*?)\\]\\(([^\\)]*?)\\)');
            task.setReplace('<a href="\\2">\\1</a>');
            task.perform();
            
            task = project.createTask('replaceregexp');
            task.setFile(tree.realTarget);
            task.setFlags('gs');
            task.setMatch('([/"])[0-9]{3}-([^.]*?)\.md');
            task.setReplace('\\1\\2.html');
            task.perform();
            
            task = project.createTask('replaceregexp');
            task.setFile(tree.realTarget);
            task.setFlags('gs');
            task.setMatch('([/"])[0-9]{3}-([a-z])');
            task.setReplace('\\1\\2');
            task.perform();
            
            // cleanup
            task = project.createTask('delete');
            task.setFile(tree.target);
            task.perform();
            
            if (tree.hasToc) {
                task = project.createTask('replace');
                task.setFile(tree.realTarget);
                task.setToken('<!--toc-->');
                task.setValue(buildToc(tree, pre));
                task.perform();
            }
            
            if (tree.hasMainToc) {
                task = project.createTask('replace');
                task.setFile(tree.realTarget);
                task.setToken('<!--main-toc-->');
                task.setValue(buildToc(globalTree, pre));
                task.perform();
            }
            
            converted++;
        }
        
        for (var i in tree.sub) {
            process(tree.sub[i]);
        }
    };
    
    // Read documentation tree and copy resources
    analyze(tree, sourceDir, destinationDir);
    
    analyze({}, new File(templateDir, "css"), new File(destinationDir, "css"));
    
    // Copy, convert, fix
    process(tree);
    
    // create contexts
    var xml = '<?xml version="1.0" encoding="UTF-8"?>\n\
    <!--\n\
        Copyright (c) 2009, ' + (new Date().getFullYear()) + ' IBM Corporation and others.\n\
        All rights reserved. This program and the accompanying materials\n\
        are made available under the terms of the Eclipse Public License v1.0\n\
        which accompanies this distribution, and is available at\n\
        http://www.eclipse.org/legal/epl-v10.\n\
            \n\
        Contributors:\n\
            IBM Corporation - initial API and implementation\n\
            Zend Technologies\n\
     -->\n\
     \n\
    <!-- Auto generated using build.xml -->\n\
    <contexts>\n';
    var file = '/*******************************************************************************\n\
     * Copyright (c) 2009, ' + (new Date().getFullYear()) + ' IBM Corporation and others.\n\
     *\n\
     * This program and the accompanying materials are made\n\
     * available under the terms of the Eclipse Public License 2.0\n\
     * which is available at https://www.eclipse.org/legal/epl-2.0/\n\
     *\n\
     * SPDX-License-Identifier: EPL-2.0\n\
     * \n\
     * Contributors:\n\
     *     IBM Corporation - initial API and implementation\n\
     *     Zend Technologies\n\
     *******************************************************************************/\n\
    package org.eclipse.php.internal.ui;\n\
    \n\
    /**\n\
     * This code was generated using build.xml\n\
     */\n\
     @SuppressWarnings("all")\n\
    public interface IPHPHelpContextIds {\n\
        \n\
        public static final String PREFIX = "org.eclipse.php.help."; //$NON-NLS-1$\n\
    ';
    
    var toc = '<?xml version="1.0" encoding="utf-8"?>\n\
    <!--\n\
        Copyright (c) 2009 IBM Corporation and others.\n\
        \n\
        This program and the accompanying materials are made\n\
        available under the terms of the Eclipse Public License 2.0\n\
        which is available at https://www.eclipse.org/legal/epl-2.0/\n\
        \n\
        SPDX-License-Identifier: EPL-2.0\n\
        \n\
        Contributors:\n\
            IBM Corporation - initial API and implementation\n\
            Zend Technologies\n\
     -->\n\
    <!--This file is generated using build.xml-->\n\
    <toc label="' + tree.title + '">\n';
    
    var createContexts = function(tree) {
        if (tree.contexts && tree.contexts.length > 0) {
            for (var i in tree.contexts) {
                var cnt = tree.contexts[i];
                file += 'public static final String ' + cnt.toUpperCase() + ' = PREFIX + "' + cnt + '"; //$NON-NLS-1$ ' + tree.source.getAbsolutePath().substring(sourceDir.getAbsolutePath().length() + 1) + '\n\n';
                xml += '<context id="' + cnt + '"><topic href="html/' + tree.url + '" label="' + tree.title + '"></topic></context>\n';
            }
        }
        if (tree.title && tree.url != 'index.html') {
            toc += '<topic label="' + tree.title + '" href="html/' + tree.url + '">\n';
        }
        if (tree.sub.length > 0) {
            
            for (var i in tree.sub) {
                createContexts(tree.sub[i]);
            }
        }
        if (tree.title && tree.url != 'index.html') {
            toc += '</topic>';
        }
    }
    
    createContexts(tree);
    file += '}';
    xml += '</contexts>';
    toc += '</toc>';
    var echo = project.createTask('echo');
    echo.setMessage(file);
    echo.setFile(new File(project.getProperty('help.context.ids')));
    echo.perform();
    
    echo = project.createTask('echo');
    echo.setMessage(xml);
    echo.setFile(new File(project.getProperty('help.context.xml')));
    echo.perform();
    
    echo = project.createTask('echo');
    echo.setMessage(toc);
    echo.setFile(new File(project.getProperty('help.toc')));
    echo.perform();

}
