package io.github.lmikoto.railgun.configurable;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.JBUI;
import io.github.lmikoto.railgun.configurable.action.TemplateAddAction;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * @author liuyang
 * 2021/3/7 5:57 下午
 */
public class TemplateConfigurable extends JBPanel implements Configurable{

    @Getter
    private Tree templateTree;

    private ToolbarDecorator toolbarDecorator;

    private TemplateEditor templateEditor;

    private JSplitPane jSplitPane;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "模版配置";
    }

    @Override
    public @Nullable JComponent createComponent() {
        init();
        return this;
    }

    private void init() {

        // init template tree
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        templateTree = new Tree();
//        templateTree.putClientProperty("JTree.lineStyle", "Horizontal");
        templateTree.setRootVisible(true);
        templateTree.setShowsRootHandles(true);
        templateTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        templateTree.setCellRenderer(new TemplateTreeCellRenderer());
        toolbarDecorator = ToolbarDecorator.createDecorator(templateTree)
                .setAddAction(new TemplateAddAction(this));
//                .setRemoveAction(new TemplateRemoveAction(this))
//                .setEditAction(new TemplateEditAction(this));

        templateTree.addTreeSelectionListener( it -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) templateTree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            Object object = node.getUserObject();
            if(object instanceof CodeTemplate) {
                CodeTemplate template = (CodeTemplate) object;
                templateEditor.getRootPanel().setVisible(true);
//                templateEditor.refresh(template);
            } else {
                templateEditor.getRootPanel().setVisible(false);
            }
        });



        JPanel templatesPanel = toolbarDecorator.createPanel();
        templatesPanel.setPreferredSize(JBUI.size(240,100));
        templateEditor = new TemplateEditor();
        jSplitPane = new JSplitPane();
        jSplitPane.setDividerLocation(240);
        jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setBorder(BorderFactory.createEmptyBorder());
        jSplitPane.setTopComponent(templatesPanel);
//        jSplitPane.setBottomComponent(templateEditor.getRootPanel());
        add(jSplitPane, BorderLayout.CENTER);
        GuiUtils.replaceJSplitPaneWithIDEASplitter(this);

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    public class TemplateTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean isLeaf, int row,boolean hasFocus) {
            if (selected) {
                setForeground(getTextSelectionColor());
            } else {
                setForeground(getTextNonSelectionColor());
            }

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Object obj = treeNode.getUserObject();
            if (obj instanceof CodeGroup) {
                CodeGroup node = (CodeGroup) obj;
                DefaultTreeCellRenderer tempCellRenderer = new DefaultTreeCellRenderer();
                tempCellRenderer.setOpenIcon(AllIcons.Nodes.JavaModule);
                tempCellRenderer.setClosedIcon(AllIcons.Nodes.JavaModule);
                tempCellRenderer.setLeafIcon(AllIcons.Nodes.JavaModule);
                return tempCellRenderer.getTreeCellRendererComponent(tree, node.getName(), selected, expanded, false, row, hasFocus);
            }
            else if (obj instanceof CodeDir) {
                CodeDir group = (CodeDir) obj;
                DefaultTreeCellRenderer tempCellRenderer = new DefaultTreeCellRenderer();
                tempCellRenderer.setOpenIcon(AllIcons.Nodes.Folder);
                tempCellRenderer.setClosedIcon(AllIcons.Nodes.Folder);
                tempCellRenderer.setLeafIcon(AllIcons.Nodes.Folder);
                return tempCellRenderer.getTreeCellRendererComponent(tree, group.getName(), selected, expanded, false, row, hasFocus);
            }
            else if (obj instanceof CodeTemplate) {
                CodeTemplate node = (CodeTemplate) obj;
                DefaultTreeCellRenderer tempCellRenderer = new DefaultTreeCellRenderer();
                return tempCellRenderer.getTreeCellRendererComponent(tree, node.getName(), selected, expanded, true, row, hasFocus);
            } else {
                String text = (String) obj;
                DefaultTreeCellRenderer tempCellRenderer = new DefaultTreeCellRenderer();
                return tempCellRenderer.getTreeCellRendererComponent(tree, text, selected, expanded, false, row, hasFocus);
            }
        }
    }
}
