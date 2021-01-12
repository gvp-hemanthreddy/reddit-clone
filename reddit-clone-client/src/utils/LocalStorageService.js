const LocalStorageService = (function () {
  var _service;

  function _getService() {
    if (!_service) {
      _service = this;
      return _service;
    }
    return _service;
  }

  function _setToken(tokenObj) {
    localStorage.setItem("access_token", tokenObj.authenticationToken);
    localStorage.setItem("refresh_token", tokenObj.refreshToken);
    localStorage.setItem("username", tokenObj.username);
  }

  function _getAccessToken() {
    return localStorage.getItem("access_token");
  }

  function _getRefreshToken() {
    return localStorage.getItem("refresh_token");
  }

  function _getUsername() {
    return localStorage.getItem("username");
  }

  function _clearTokens() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("username");
  }

  return {
    getService: _getService,
    setToken: _setToken,
    getAccessToken: _getAccessToken,
    getRefreshToken: _getRefreshToken,
    getUsername: _getUsername,
    clearTokens: _clearTokens,
  };
})();

export default LocalStorageService;
