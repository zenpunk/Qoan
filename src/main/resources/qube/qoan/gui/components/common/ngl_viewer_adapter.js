

// corresponds to constructor of the class
qube_qoan_gui_components_common_NglAdapter = function() {

  var e = this.getElement();

  this.onStateChange = function() {
    e.innerHTML = this.getState().xhtml;
  }

  e.innerHTML = this.getState().xhtml;
  if( !Detector.webgl ) Detector.addGetWebGLMessage();

  // /VAADIN/themes/mytheme/
  NGL.mainScriptFilePath = "/VAADIN/js/ngl.embedded.min.js";

  //function onInit(){
  //  var stage = new NGL.Stage("viewport");
  //  stage.loadFile( "rcsb://1crn", { defaultRepresentation: true } );
  //}

  //document.addEventListener( "DOMContentLoaded", function() {
  //	    NGL.init( onInit );
  // } );

}

function onInit(){
    var stage = new NGL.Stage("viewport");
    stage.loadFile( "rcsb://1crn", { defaultRepresentation: true } );
}

function onInitLoad(name) {
    var stage = new NGL.Stage("viewport");
    stage.loadFile( "rcsb://" + name, { defaultRepresentation: true } );
}