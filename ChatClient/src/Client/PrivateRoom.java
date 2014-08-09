/*
 * PrivateRoom.java
 *
 * Created on April 7, 2008, 1:22 AM
 */

package Client;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author  Udhay
 */
public class PrivateRoom extends javax.swing.JFrame {
    String MyName="",SndName="",FileName="",DirPath="",FilePath="",FileNameGot="";                //MyName  - My nick Name.... SndName - Pair of the chat Room
    Socket Soket=null;
    DataOutputStream dos=null;
    
    /** Creates new form PrivateRoom */
   
    public PrivateRoom(String MyName,String SndName,Socket Sock) {
        initComponents();
        this.MyName=MyName;
        this.SndName=SndName;
        this.setResizable(false);
        this.setDefaultLookAndFeelDecorated(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Soket=Sock;
        try {
            dos=new DataOutputStream(Soket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.setBounds(0,0,390,420);
        filechoosePanel.setBounds(0,0,390,410);
        filechoosePanel.setVisible(false);
        SenderName.setText(SndName);
    }
    
    public void UpdateMsgArea(String Msg)
    {
        PrivRoomtxt.setText(PrivRoomtxt.getText()+Msg+"\n");        
    }
    
    public void CloseMessage()
    {
        JOptionPane.showMessageDialog(this,SndName+" had left the Room...");
        Sendbtn.setEnabled(false); 
        this.dispose();
    }
    
    public void CreatePlaceToSave(String FN)
    {
        try 
        {
            FileNameGot=FN;
            //JOptionPane.showMessageDialog(this,SndName+" Want to ")
            int opt=JOptionPane.showConfirmDialog(this,SndName+" Wanna Send a File '"+FN+"' to You... ","Are You Ready..",0,3);       
            if(opt==0) 
            {                
                filechoosePanel.setVisible(true);
                FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);                
                filechoosePanel.setBounds(20,20,400,400);
                int option = FileChooser.showOpenDialog(this);
                if(option==FileChooser.APPROVE_OPTION) 
                {                    
                    File f=FileChooser.getSelectedFile();
                    DirPath=f.getAbsolutePath();
                    DirPath=DirPath+"\\"+FN;                                        
                    filenametxt.setText(DirPath);                                       
                    filechoosePanel.setVisible(false);
                    
                    dos.writeUTF("10|1|"+SndName);      // Message to say server that ready to receive from SndName...
                }
                else
                {                    
                    dos.writeUTF("10|0|"+SndName);
                }
            }
            else 
            {             
              dos.writeUTF("10|0|"+SndName);       //Message to say server that its not wanna receive file...
            }
          }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void ReceiveFile()
    {
        try
        {
            Socket s=new Socket("127.0.0.1",20);
            BufferedInputStream inFromServer = new BufferedInputStream(s.getInputStream());            
            String NewDirName="";
            StringTokenizer toke=new StringTokenizer(DirPath,"\\");
            NewDirName=toke.nextToken();
            while(toke.hasMoreTokens())
            {
                NewDirName=NewDirName+"\\\\"+toke.nextToken();                        
            }
            FileOutputStream fos = new FileOutputStream(NewDirName);
            int totalDataRead=0;
            int totalSizeWritten = 0;
            int DATA_SIZE = 20480;
            byte[] inData = new byte[DATA_SIZE];

            
            while ((totalDataRead = inFromServer.read(inData, 0, inData.length)) >= 0) {
                fos.write(inData, 0, totalDataRead);
                totalSizeWritten = totalSizeWritten + totalDataRead;
                System.out.println(totalSizeWritten);
            }
            JOptionPane.showMessageDialog(this,"File Transfered Successfully...");
            System.out.println("Done");
            inFromServer.close();
            fos.close();
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void SendFile()
    {
        try
        {
            int data;
            int totalSizeTransferred = 0;
            int totalSizeRead;
            int PACKET_SIZE = 20480;
            byte[] packet = new byte[PACKET_SIZE];
            String NewFileName="";
            Socket s=new Socket("172.16.35.42",20);
            BufferedOutputStream outToServer = new BufferedOutputStream(s.getOutputStream());            
            
            StringTokenizer toke=new StringTokenizer(FilePath,"\\");
            NewFileName+=toke.nextToken();
            while(toke.hasMoreTokens())
            {
                NewFileName=NewFileName+"\\\\"+toke.nextToken();                        
            }            
            
            FileInputStream fis = new FileInputStream(NewFileName);
            
            while ((totalSizeRead = fis.read(packet, 0, packet.length)) >= 0) {
                outToServer.write(packet, 0, totalSizeRead);
                totalSizeTransferred = totalSizeTransferred + totalSizeRead;
                System.out.println(totalSizeTransferred);
            }
            
            System.out.println("done reading file...");
            outToServer.close();
            fis.close();
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void NoWillMessage()
    {
        JOptionPane.showMessageDialog(this,SndName+" not wanna receive the file...");
        filenametxt.setText("");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roomPanel = new javax.swing.JPanel();
        closebtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PrivRoomtxt = new javax.swing.JTextArea();
        ChatWithlbl = new javax.swing.JLabel();
        clearbtn = new javax.swing.JButton();
        MsgToSendtxt = new javax.swing.JTextField();
        Sendbtn = new javax.swing.JButton();
        SenderName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filenametxt = new javax.swing.JTextField();
        ChatWithlbl1 = new javax.swing.JLabel();
        uploadbtn = new javax.swing.JButton();
        browsebtn = new javax.swing.JButton();
        smile1 = new javax.swing.JLabel();
        filechoosePanel = new javax.swing.JPanel();
        FileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 0));
        getContentPane().setLayout(null);

        roomPanel.setBackground(new java.awt.Color(153, 153, 255));
        roomPanel.setBorder(new javax.swing.border.MatteBorder(null));
        roomPanel.setLayout(null);

        closebtn.setFont(new java.awt.Font("Broadway", 0, 14));
        closebtn.setText("CLOSE");
        closebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtnActionPerformed(evt);
            }
        });
        roomPanel.add(closebtn);
        closebtn.setBounds(260, 10, 90, 25);

        PrivRoomtxt.setColumns(20);
        PrivRoomtxt.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        PrivRoomtxt.setRows(5);
        jScrollPane1.setViewportView(PrivRoomtxt);

        roomPanel.add(jScrollPane1);
        jScrollPane1.setBounds(30, 50, 330, 130);

        ChatWithlbl.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 12));
        ChatWithlbl.setText("Chat with : ");
        roomPanel.add(ChatWithlbl);
        ChatWithlbl.setBounds(30, 20, 100, 20);

        clearbtn.setFont(new java.awt.Font("Broadway", 0, 14));
        clearbtn.setText("CLEAR");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        roomPanel.add(clearbtn);
        clearbtn.setBounds(30, 230, 90, 25);

        MsgToSendtxt.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        roomPanel.add(MsgToSendtxt);
        MsgToSendtxt.setBounds(30, 190, 330, 29);

        Sendbtn.setFont(new java.awt.Font("Broadway", 0, 14));
        Sendbtn.setText("SEND");
        Sendbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendbtnActionPerformed(evt);
            }
        });
        roomPanel.add(Sendbtn);
        Sendbtn.setBounds(280, 230, 80, 25);

        SenderName.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        roomPanel.add(SenderName);
        SenderName.setBounds(100, 20, 150, 20);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setLayout(null);

        filenametxt.setFont(new java.awt.Font("Arial", 0, 14));
        jPanel1.add(filenametxt);
        filenametxt.setBounds(10, 40, 200, 20);

        ChatWithlbl1.setFont(new java.awt.Font("Impact", 0, 14));
        ChatWithlbl1.setText("* SHARE YOUR FILES HERE *");
        jPanel1.add(ChatWithlbl1);
        ChatWithlbl1.setBounds(70, 10, 150, 20);

        uploadbtn.setFont(new java.awt.Font("Stencil", 0, 14));
        uploadbtn.setText("UPLOAD");
        uploadbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadbtnActionPerformed(evt);
            }
        });
        jPanel1.add(uploadbtn);
        uploadbtn.setBounds(110, 70, 100, 30);

        browsebtn.setFont(new java.awt.Font("Stencil", 0, 14));
        browsebtn.setText("BROWSE");
        browsebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsebtnActionPerformed(evt);
            }
        });
        jPanel1.add(browsebtn);
        browsebtn.setBounds(220, 30, 100, 30);

        roomPanel.add(jPanel1);
        jPanel1.setBounds(30, 270, 330, 110);

        smile1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/vault.png"))); // NOI18N
        roomPanel.add(smile1);
        smile1.setBounds(140, 200, 130, 140);

        getContentPane().add(roomPanel);
        roomPanel.setBounds(0, 0, 390, 400);

        FileChooser.setFont(new java.awt.Font("Arial Black", 0, 11));
        FileChooser.setForeground(new java.awt.Color(51, 51, 255));
        FileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileChooserActionPerformed(evt);
            }
        });
        filechoosePanel.add(FileChooser);

        getContentPane().add(filechoosePanel);
        filechoosePanel.setBounds(510, 20, 590, 410);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browsebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsebtnActionPerformed
           filechoosePanel.setVisible(true);
           filechoosePanel.setBounds(20,20,400,410);  
           FileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
           
           int option = FileChooser.showOpenDialog(this);
           
           if(option==FileChooser.APPROVE_OPTION)
           {            
               File f=FileChooser.getSelectedFile();
               FilePath=f.getAbsolutePath();
               FileName=f.getName();
               filenametxt.setText(FilePath);
               JOptionPane.showMessageDialog(this,FilePath);
               filechoosePanel.setVisible(false);
           }
           else
           {               
               filechoosePanel.setVisible(false);
           }
    }//GEN-LAST:event_browsebtnActionPerformed

    private void FileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileChooserActionPerformed
       
    }//GEN-LAST:event_FileChooserActionPerformed

    private void uploadbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadbtnActionPerformed
        try
        {
            String Msg="9|"+SndName+"|"+FileName;        
            dos=new DataOutputStream(Soket.getOutputStream());
            //Message from source client to server...
            dos.writeUTF(Msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }           
    }//GEN-LAST:event_uploadbtnActionPerformed

    private void closebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtnActionPerformed
        try
        {
            dos=new DataOutputStream(Soket.getOutputStream());
            dos.writeUTF("8|"+MyName);
            this.dispose();            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_closebtnActionPerformed

    private void SendbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendbtnActionPerformed
        try
        {
            String Msg=MsgToSendtxt.getText();
            Msg="6|"+SndName+"|"+Msg;
            dos=new DataOutputStream(Soket.getOutputStream());
            dos.writeUTF(Msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_SendbtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        MsgToSendtxt.setText("");
    }//GEN-LAST:event_clearbtnActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrivateRoom().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChatWithlbl;
    private javax.swing.JLabel ChatWithlbl1;
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JTextField MsgToSendtxt;
    private javax.swing.JTextArea PrivRoomtxt;
    private javax.swing.JButton Sendbtn;
    private javax.swing.JLabel SenderName;
    private javax.swing.JButton browsebtn;
    private javax.swing.JButton clearbtn;
    private javax.swing.JButton closebtn;
    private javax.swing.JPanel filechoosePanel;
    private javax.swing.JTextField filenametxt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel roomPanel;
    private javax.swing.JLabel smile1;
    private javax.swing.JButton uploadbtn;
    // End of variables declaration//GEN-END:variables
    
}
