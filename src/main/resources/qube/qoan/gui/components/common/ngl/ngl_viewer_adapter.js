

// corresponds to constructor of the class
qube_qoan_gui_components_common_NglAdapter = function() {

  var e = this.getElement();

  //this.onStateChange = function() {
  //  e.innerHTML = this.getState().xhtml;
  //}

  e.innerHTML = this.getState().xhtml;
  //if( !Detector.webgl ) Detector.addGetWebGLMessage();

  //NGL.mainScriptFilePath = "qube/qoan/gui/components/common/ngl/ngl.embedded.min.js";

  function onInit(){
    var stage = new NGL.Stage( "viewport" );
        stage.loadFile( "1CRN.pdb", { defaultRepresentation: true } );
  }

  NGL.init( onInit );

  //document.addEventListener( "DOMContentLoaded", function() {
  //    NGL.init( onInit );
  //} );
}