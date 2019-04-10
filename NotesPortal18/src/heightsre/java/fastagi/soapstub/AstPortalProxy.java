package heightsre.java.fastagi.soapstub;

public class AstPortalProxy implements heightsre.java.fastagi.soapstub.AstPortal {
  private String _endpoint = null;
  private heightsre.java.fastagi.soapstub.AstPortal astPortal = null;
  
  public AstPortalProxy() {
    _initAstPortalProxy();
  }
  
  public AstPortalProxy(String endpoint) {
    _endpoint = endpoint;
    _initAstPortalProxy();
  }
  
  private void _initAstPortalProxy() {
    try {
      astPortal = (new heightsre.java.fastagi.soapstub.AstPortalServiceLocator()).getDomino();
      if (astPortal != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)astPortal)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)astPortal)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (astPortal != null)
      ((javax.xml.rpc.Stub)astPortal)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public heightsre.java.fastagi.soapstub.AstPortal getAstPortal() {
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal;
  }
  
  public java.lang.String getTest() throws java.rmi.RemoteException{
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal.getTest();
  }
  
  public java.lang.String[] getPositionByBuildingCode(java.lang.String buildingcode) throws java.rmi.RemoteException{
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal.getPositionByBuildingCode(buildingcode);
  }
  
  public java.lang.String setDocRecording(java.lang.String docid, java.lang.String recid) throws java.rmi.RemoteException{
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal.setDocRecording(docid, recid);
  }
  
  public java.lang.String[] generalCommand(java.lang.String cmd, java.lang.String[] options, byte[] data) throws java.rmi.RemoteException{
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal.generalCommand(cmd, options, data);
  }
  
  public java.lang.String[] generalCommand(java.lang.String cmd, java.lang.String[] options) throws java.rmi.RemoteException{
    if (astPortal == null)
      _initAstPortalProxy();
    return astPortal.generalCommand(cmd, options);
  }
  
  
}