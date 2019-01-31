package com.automate.build.maven;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/31 16:48
 */
public class MavenPomReader {
    private static Logger logger = LoggerFactory.getLogger(MavenPomReader.class);

    public void read(File dir) {
        //遍历项目下面的 所有 pom.xml文件
        Collection<File> list = FileUtils.listFiles(dir, FileFilterUtils.nameFileFilter("pom.xml"), DirectoryFileFilter.INSTANCE);

    }

    private static MavenProject readPomXml(File pomXmlFile) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(pomXmlFile);
            Element projectEl = document.getRootElement();
            String groupId = projectEl.elementText("groupId");
            if (groupId == null) {
                Element parentEL = projectEl.element("parent");
                if (parentEL != null) {
                    groupId = parentEL.elementText("groupId");
                }
            }
            String artifactId = projectEl.elementText("artifactId");
            if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(artifactId)) {
                logger.warn("未找到groupId或artifactId:{}", pomXmlFile.getAbsolutePath());
                return null;
            }
            String packaging = projectEl.elementText("packaging");
            MavenProject.PackagingType packagingType = MavenProject.PackagingType.match(packaging);
            if (packagingType == MavenProject.PackagingType.pom) {
                //pom 不需要
                return null;
            }

            MavenProject mavenProject = new MavenProject(groupId, artifactId, packagingType);

            Element dependenciesEl = projectEl.element("dependencies");
            if (dependenciesEl != null) {
                List<Element> dependencyList = dependenciesEl.elements("dependency");
                if (dependencyList.size() > 0) {
                   List<String> list = new ArrayList<>(dependencyList.size());
                    for (Element dependencyEl : dependencyList) {
                        list.add(MavenProject.buildKey(dependencyEl.elementText("groupId"), dependencyEl.elementText("artifactId")));
                    }
                    mavenProject.setDependenciesList(list);
                }
            }
            return mavenProject;

        } catch (DocumentException e) {
            logger.error("读取pom.xml失败", e);
        }

        return null;
    }
}
