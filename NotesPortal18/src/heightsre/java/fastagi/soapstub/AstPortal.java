/**
 * AstPortal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package heightsre.java.fastagi.soapstub;

public interface AstPortal extends java.rmi.Remote {
    public java.lang.String getTest() throws java.rmi.RemoteException;
    public java.lang.String[] getPositionByBuildingCode(java.lang.String buildingcode) throws java.rmi.RemoteException;
    public java.lang.String setDocRecording(java.lang.String docid, java.lang.String recid) throws java.rmi.RemoteException;
    public java.lang.String[] generalCommand(java.lang.String cmd, java.lang.String[] options, byte[] data) throws java.rmi.RemoteException;
    public java.lang.String[] generalCommand(java.lang.String cmd, java.lang.String[] options) throws java.rmi.RemoteException;
}
