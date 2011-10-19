/*
Copyright 2008-2010 Gephi
Authors : Patick J. McSweeney <pjmcswee@syr.edu>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gephi.ui.statistics.plugin;

import org.gephi.graph.api.GraphController;
import org.openide.util.Lookup;

/**
 *
 * @author pjmcswee
 */
public class HitsPanel extends javax.swing.JPanel {

    public HitsPanel() {
        initComponents();
        
        //Disable directed if the graph is undirecteds
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        if(graphController.getModel().isUndirected()){
            directedRadioButton.setEnabled(false);
        }
    }

    public boolean isDirected() {
        return directedRadioButton.isSelected();
    }

    public double getEpsilon() {
        try {
            return Double.parseDouble(epsilonTextField.getText());
        } catch (Exception e) {
        }
        return 0.0001;
    }

    public void setDirected(boolean directed) {
        directedButtonGroup.setSelected(directed ? directedRadioButton.getModel() : undirectedRadioButton.getModel(), true);
        if (!directed) {
            directedRadioButton.setEnabled(false);
        }
    }

    public void setEpsilon(double epsilon) {
        epsilonTextField.setText(Double.toString(epsilon));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directedButtonGroup = new javax.swing.ButtonGroup();
        labelEpsilon = new javax.swing.JLabel();
        epsilonTextField = new javax.swing.JTextField();
        undirectedRadioButton = new javax.swing.JRadioButton();
        directedRadioButton = new javax.swing.JRadioButton();
        descriptionLabel = new org.jdesktop.swingx.JXLabel();
        header = new org.jdesktop.swingx.JXHeader();
        epsilonLabel = new org.jdesktop.swingx.JXLabel();

        labelEpsilon.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.labelEpsilon.text")); // NOI18N

        epsilonTextField.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.epsilonTextField.text")); // NOI18N
        epsilonTextField.setMinimumSize(new java.awt.Dimension(59, 25));
        epsilonTextField.setPreferredSize(new java.awt.Dimension(59, 25));

        directedButtonGroup.add(undirectedRadioButton);
        undirectedRadioButton.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.undirectedRadioButton.text")); // NOI18N

        directedButtonGroup.add(directedRadioButton);
        directedRadioButton.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.directedRadioButton.text")); // NOI18N

        descriptionLabel.setLineWrap(true);
        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.descriptionLabel.text")); // NOI18N
        descriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        header.setDescription(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.header.description")); // NOI18N
        header.setTitle(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.header.title")); // NOI18N

        epsilonLabel.setForeground(new java.awt.Color(102, 102, 102));
        epsilonLabel.setLineWrap(true);
        epsilonLabel.setText(org.openide.util.NbBundle.getMessage(HitsPanel.class, "HitsPanel.epsilonLabel.text")); // NOI18N
        epsilonLabel.setFont(epsilonLabel.getFont().deriveFont(epsilonLabel.getFont().getSize()-1f));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(directedRadioButton)
                            .addComponent(undirectedRadioButton))
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelEpsilon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(epsilonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(143, 143, 143))
                            .addComponent(epsilonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directedRadioButton)
                    .addComponent(labelEpsilon)
                    .addComponent(epsilonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(undirectedRadioButton)
                    .addComponent(epsilonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXLabel descriptionLabel;
    private javax.swing.ButtonGroup directedButtonGroup;
    protected javax.swing.JRadioButton directedRadioButton;
    private org.jdesktop.swingx.JXLabel epsilonLabel;
    protected javax.swing.JTextField epsilonTextField;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel labelEpsilon;
    protected javax.swing.JRadioButton undirectedRadioButton;
    // End of variables declaration//GEN-END:variables
}
