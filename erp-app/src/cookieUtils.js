export function getCookie(name) {
    let cookieArray = document.cookie.split(';'); // Split the cookie string into an array
  
    for(let i = 0; i < cookieArray.length; i++) {
      let cookie = cookieArray[i];
      while (cookie.charAt(0) === ' ') {
        cookie = cookie.substring(1);
      }
      if (cookie.indexOf(name + "=") === 0) {
        return cookie.substring(name.length + 1, cookie.length);
      }
    }
    return "";
  }