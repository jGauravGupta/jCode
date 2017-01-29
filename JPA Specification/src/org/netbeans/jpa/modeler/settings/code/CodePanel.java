/**
 * Copyright [2016] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.settings.code;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

public final class CodePanel extends javax.swing.JPanel {

    private final CodeOptionsPanelController controller;

    CodePanel(CodeOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        // TODO listen to changes in form fields and call controller.changed()
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        refractorNamedQueryComp = new javax.swing.JCheckBox();
        deleteNamedQueryComp = new javax.swing.JCheckBox();
        generateFluentAPIComp = new javax.swing.JCheckBox();
        compositePKWrapperPanel = new javax.swing.JLayeredPane();
        compositePrimaryKeyTypeLabel = new javax.swing.JLabel();
        defaultCompositePrimaryKeyTypeComp = new javax.swing.JComboBox<>();
        optionalReturnTypePanel = new javax.swing.JLayeredPane();
        optionalReturnTypeStatusLabel = new javax.swing.JLabel();
        optionalReturnTypeStatusComp = new javax.swing.JComboBox<>();
        javaseWrapperPanel = new javax.swing.JLayeredPane();
        javaseSupportLabel = new javax.swing.JLabel();
        javaseSupportComp = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(refractorNamedQueryComp, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.refractorNamedQueryComp.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deleteNamedQueryComp, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.deleteNamedQueryComp.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(generateFluentAPIComp, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.generateFluentAPIComp.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(compositePrimaryKeyTypeLabel, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.compositePrimaryKeyTypeLabel.text")); // NOI18N

        defaultCompositePrimaryKeyTypeComp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IdClass", "EmbeddedId" }));

        compositePKWrapperPanel.setLayer(compositePrimaryKeyTypeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        compositePKWrapperPanel.setLayer(defaultCompositePrimaryKeyTypeComp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout compositePKWrapperPanelLayout = new javax.swing.GroupLayout(compositePKWrapperPanel);
        compositePKWrapperPanel.setLayout(compositePKWrapperPanelLayout);
        compositePKWrapperPanelLayout.setHorizontalGroup(
            compositePKWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compositePKWrapperPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(compositePrimaryKeyTypeLabel)
                .addGap(18, 18, 18)
                .addComponent(defaultCompositePrimaryKeyTypeComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        compositePKWrapperPanelLayout.setVerticalGroup(
            compositePKWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, compositePKWrapperPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(compositePKWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compositePrimaryKeyTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultCompositePrimaryKeyTypeComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(optionalReturnTypeStatusLabel, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.optionalReturnTypeStatusLabel.text")); // NOI18N

        optionalReturnTypeStatusComp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disable", "Enable" }));

        optionalReturnTypePanel.setLayer(optionalReturnTypeStatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        optionalReturnTypePanel.setLayer(optionalReturnTypeStatusComp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout optionalReturnTypePanelLayout = new javax.swing.GroupLayout(optionalReturnTypePanel);
        optionalReturnTypePanel.setLayout(optionalReturnTypePanelLayout);
        optionalReturnTypePanelLayout.setHorizontalGroup(
            optionalReturnTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionalReturnTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optionalReturnTypeStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(optionalReturnTypeStatusComp, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        optionalReturnTypePanelLayout.setVerticalGroup(
            optionalReturnTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionalReturnTypePanelLayout.createSequentialGroup()
                .addGroup(optionalReturnTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optionalReturnTypeStatusLabel)
                    .addComponent(optionalReturnTypeStatusComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        javaseSupportLabel.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(javaseSupportLabel, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.javaseSupportLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(javaseSupportComp, org.openide.util.NbBundle.getMessage(CodePanel.class, "CodePanel.javaseSupportComp.text")); // NOI18N

        javaseWrapperPanel.setLayer(javaseSupportLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        javaseWrapperPanel.setLayer(javaseSupportComp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout javaseWrapperPanelLayout = new javax.swing.GroupLayout(javaseWrapperPanel);
        javaseWrapperPanel.setLayout(javaseWrapperPanelLayout);
        javaseWrapperPanelLayout.setHorizontalGroup(
            javaseWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javaseWrapperPanelLayout.createSequentialGroup()
                .addComponent(javaseSupportComp, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(javaseSupportLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        javaseWrapperPanelLayout.setVerticalGroup(
            javaseWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javaseWrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(javaseSupportLabel)
                .addComponent(javaseSupportComp))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteNamedQueryComp)
                    .addComponent(refractorNamedQueryComp)
                    .addComponent(generateFluentAPIComp)
                    .addComponent(javaseWrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compositePKWrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optionalReturnTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(refractorNamedQueryComp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteNamedQueryComp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generateFluentAPIComp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(javaseWrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(compositePKWrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionalReturnTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    void load() {
         refractorNamedQueryComp.setSelected(isRefractorQuery());
         deleteNamedQueryComp.setSelected(isDeleteQuery());
         generateFluentAPIComp.setSelected(isGenerateFluentAPI());
         javaseSupportComp.setSelected(isJavaSESupportEnable());
         optionalReturnTypeStatusComp.setSelectedItem(isOptionalReturnType()?"Enable":"Disable");
         defaultCompositePrimaryKeyTypeComp.setSelectedItem(getDefaultCompositePrimaryKeyType());
    }

    void store() {
       pref.putBoolean("refractorNamedQuery", refractorNamedQueryComp.isSelected());
       pref.putBoolean("deleteNamedQuery", deleteNamedQueryComp.isSelected());
       pref.putBoolean("generateFluentAPI", generateFluentAPIComp.isSelected());
       pref.putBoolean("javaSESupportEnable", javaseSupportComp.isSelected());
       pref.putBoolean("optionalReturnType", "Enable".equals(optionalReturnTypeStatusComp.getSelectedItem()));
       pref.put("defaultCompositePrimaryKeyType", (String)defaultCompositePrimaryKeyTypeComp.getSelectedItem());
        deleteNamedQuery = null;
        refractorNamedQuery = null;
        generateFluentAPI = null;
        javaSESupportEnable = null;    
        optionalReturnTypeStatus = null;
        defaultCompositePrimaryKeyType = null;
    }

    private static Boolean deleteNamedQuery;
    private static Boolean refractorNamedQuery;
    private static Boolean generateFluentAPI;
    private static Boolean javaSESupportEnable;
    private static Boolean optionalReturnTypeStatus;
    private static String defaultCompositePrimaryKeyType;
    
    public static boolean isRefractorQuery() {
        if (refractorNamedQuery == null) {
            refractorNamedQuery = pref.getBoolean("refractorNamedQuery", Boolean.TRUE);
        }
        return refractorNamedQuery;
    }
    
    public static boolean isGenerateFluentAPI() {
        if (generateFluentAPI == null) {
            generateFluentAPI = pref.getBoolean("generateFluentAPI", Boolean.FALSE);
        }
        return generateFluentAPI;
    }
    
    public static boolean isJavaSESupportEnable() {
        if (javaSESupportEnable == null) {
            javaSESupportEnable = pref.getBoolean("javaSESupportEnable", Boolean.FALSE);
        }
        return javaSESupportEnable;
    }
    
    public static boolean isOptionalReturnType() {
        if (optionalReturnTypeStatus == null) {
            optionalReturnTypeStatus = pref.getBoolean("optionalReturnType", Boolean.FALSE);
        }
        return optionalReturnTypeStatus;
    }
    
    public static boolean isDeleteQuery(){
        if (deleteNamedQuery == null) {
            deleteNamedQuery = pref.getBoolean("deleteNamedQuery", Boolean.FALSE);
        }
        return deleteNamedQuery;
    }
    public static String getDefaultCompositePrimaryKeyType(){
        if (defaultCompositePrimaryKeyType == null) {
            defaultCompositePrimaryKeyType = pref.get("defaultCompositePrimaryKeyType", "IdClass");
        }
        return defaultCompositePrimaryKeyType;
    }
    
    public static boolean isEmbeddedIdDefaultType(){
        return "EmbeddedId".equals(getDefaultCompositePrimaryKeyType());
    }
    
    public boolean valid(){
        return true;
    }
    
private static final Preferences pref = NbPreferences.forModule(CodePanel.class);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane compositePKWrapperPanel;
    private javax.swing.JLabel compositePrimaryKeyTypeLabel;
    private javax.swing.JComboBox<String> defaultCompositePrimaryKeyTypeComp;
    private javax.swing.JCheckBox deleteNamedQueryComp;
    private javax.swing.JCheckBox generateFluentAPIComp;
    private javax.swing.JCheckBox javaseSupportComp;
    private javax.swing.JLabel javaseSupportLabel;
    private javax.swing.JLayeredPane javaseWrapperPanel;
    private javax.swing.JLayeredPane optionalReturnTypePanel;
    private javax.swing.JComboBox<String> optionalReturnTypeStatusComp;
    private javax.swing.JLabel optionalReturnTypeStatusLabel;
    private javax.swing.JCheckBox refractorNamedQueryComp;
    // End of variables declaration//GEN-END:variables
}
