qube_qoan_gui_components_common_NglAdapter = function() {
  var e = this.getElement();

  this.onStateChange = function() {
    e.innerHTML = this.getState().xhtml;
  }
}