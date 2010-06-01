/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StreamingEndpointPanel.java
 *
 * Created on Jun 1, 2010, 4:10:01 PM
 */

package org.gephi.desktop.streaming;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import org.gephi.streaming.api.GraphStreamingEndpoint;
import org.gephi.streaming.api.StreamType;
import org.netbeans.validation.api.Problems;
import org.netbeans.validation.api.Validator;
import org.netbeans.validation.api.builtin.Validators;
import org.netbeans.validation.api.ui.ValidationGroup;
import org.netbeans.validation.api.ui.ValidationListener;
import org.netbeans.validation.api.ui.ValidationPanel;
import org.openide.util.Lookup;

/**
 *
 * @author panisson
 */
public class StreamingEndpointPanel extends javax.swing.JPanel {

    private static final String DGS_RESOURCE = "amazon_0201485419_400.dgs";

    private GraphStreamingEndpoint endpoint;
    private URL defaultUrl;

    /** Creates new form StreamingEndpointPanel */
    public StreamingEndpointPanel() {
        initComponents();

        defaultUrl = this.getClass().getResource(DGS_RESOURCE);

        streamTypeComboBox.setModel(new StreamTypeComboBoxModel());
        streamUrlTextField.setText(defaultUrl.toString());

        endpoint = new GraphStreamingEndpoint();
        endpoint.setStreamType((StreamType)streamTypeComboBox.getSelectedItem());
        endpoint.setUrl(defaultUrl);
    }

    private static class StreamTypeComboBoxModel extends DefaultComboBoxModel {

        public StreamTypeComboBoxModel() {
            super(getStreamTypes());
        }

        private static StreamType[] getStreamTypes() {
            Collection<? extends StreamType> streamTypes = Lookup.getDefault().lookupAll(StreamType.class);
            return streamTypes.toArray(new StreamType[0]);
        }

    }

     public static ValidationPanel createValidationPanel(final StreamingEndpointPanel innerPanel) {
        ValidationPanel validationPanel = new ValidationPanel();
        if (innerPanel == null) {
            throw new NullPointerException();
        }
        validationPanel.setInnerComponent(innerPanel);

        ValidationGroup group = validationPanel.getValidationGroup();
        group.add(innerPanel.streamUrlTextField, Validators.REQUIRE_NON_EMPTY_STRING);
        group.add(innerPanel.streamUrlTextField, new Validator<String>() {
            @Override
            public boolean validate(Problems prblms, String string, String urlString) {
                try {
                    URL url = new URL(urlString);
                    innerPanel.getGraphStreamingEndpoint().setUrl(url);
                } catch(MalformedURLException e) {
                    //e.printStackTrace();
                    innerPanel.getGraphStreamingEndpoint().setUrl(null);
                    prblms.add("Stream URL: invalid format");
                    return false;
                }
                return true;
            }

        });

        return validationPanel;
     }

     public GraphStreamingEndpoint getGraphStreamingEndpoint() {
         return endpoint;
     }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        streamUrlLabel = new javax.swing.JLabel();
        streamUrlTextField = new javax.swing.JTextField();
        streamTypeLabel = new javax.swing.JLabel();
        streamTypeComboBox = new javax.swing.JComboBox();

        streamUrlLabel.setText(org.openide.util.NbBundle.getMessage(StreamingEndpointPanel.class, "StreamingEndpointPanel.streamUrlLabel.text")); // NOI18N

        streamUrlTextField.setText(org.openide.util.NbBundle.getMessage(StreamingEndpointPanel.class, "StreamingEndpointPanel.streamUrlTextField.text")); // NOI18N
        streamUrlTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streamUrlTextFieldActionPerformed(evt);
            }
        });
        streamUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                streamUrlTextFieldFocusLost(evt);
            }
        });

        streamTypeLabel.setText(org.openide.util.NbBundle.getMessage(StreamingEndpointPanel.class, "StreamingEndpointPanel.streamTypeLabel.text")); // NOI18N

        streamTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DGS", "XML", "JSON" }));
        streamTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streamTypeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(streamUrlLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(streamTypeLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(streamTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(streamUrlTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(streamUrlLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(streamUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(streamTypeLabel)
                    .addComponent(streamTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void streamUrlTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streamUrlTextFieldActionPerformed
        String urlString = streamUrlTextField.getText();
        try {
            URL url = new URL(urlString);
            this.endpoint.setUrl(url);
        } catch(MalformedURLException e) {
            e.printStackTrace();
            this.endpoint.setUrl(null);
        }
        
    }//GEN-LAST:event_streamUrlTextFieldActionPerformed

    private void streamTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streamTypeComboBoxActionPerformed
        StreamType streamType = (StreamType)streamTypeComboBox.getSelectedItem();
        this.endpoint.setStreamType(streamType);
    }//GEN-LAST:event_streamTypeComboBoxActionPerformed

    private void streamUrlTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_streamUrlTextFieldFocusLost
        String urlString = streamUrlTextField.getText();
        try {
            URL url = new URL(urlString);
            this.endpoint.setUrl(url);
        } catch(MalformedURLException e) {
            e.printStackTrace();
            this.endpoint.setUrl(null);
        }
    }//GEN-LAST:event_streamUrlTextFieldFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox streamTypeComboBox;
    private javax.swing.JLabel streamTypeLabel;
    private javax.swing.JLabel streamUrlLabel;
    private javax.swing.JTextField streamUrlTextField;
    // End of variables declaration//GEN-END:variables

}
