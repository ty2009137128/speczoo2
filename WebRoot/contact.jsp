<!DOCTYPE html>
<!-- Website template by freewebsitetemplates.com -->
<html>
  <head>
    <meta charset="UTF-8">
    <title>Contact - Data-mining Based Expert Platform for the Spectral Inspection</title>
    <link rel="stylesheet" href="resources/css/style.css" type="text/css">
  </head>
  <body>
    <jsp:include page="jsp/layout/header.jsp"/>
    <div id="contents">
      <div id="contact" class="wrapper clearfix">
        <jsp:include page="jsp/layout/sidebar.jsp"/>
        <div class="main">
          <h1>Contact Us</h1>
          <form action="index.html" method="post">
            <ul>
              <li>
                <label>Enter your full name here.</label>
                <input type="text" value="Full Name" onBlur="javascript:if (this.value == '') {
                                  this.value = this.defaultValue;
                                }" onFocus="javascript:if (this.value == this.defaultValue) {
                                  this.value = '';
                                }">
              </li>
              <li>
                <label>Enter your email address here.</label>
                <input type="text" value="Email Address" onBlur="javascript:if (this.value == '') {
                                  this.value = this.defaultValue;
                                }" onFocus="javascript:if (this.value == this.defaultValue) {
                                  this.value = '';
                                }">
              </li>
              <li>
                <label>Enter the Subject message here.</label>
                <input type="text" value="Subject" onBlur="javascript:if (this.value == '') {
                                  this.value = this.defaultValue;
                                }" onFocus="javascript:if (this.value == this.defaultValue) {
                                  this.value = '';
                                }">
              </li>
              <li>
                <label class="msg">Enter your Message here.</label>
                <textarea onBlur="javascript:if (this.value == '') {
                                  this.value = this.defaultValue;
                                }" onFocus="javascript:if (this.value == this.defaultValue) {
                                  this.value = '';
                                }">Message</textarea>
                <div class="checkbox">
                  <label for="terms">
                    <input type="checkbox" id="terms">
                    I agree to the Terms and Conditions</label>
                  <br>
                  <label for="subscribe">
                    <input type="checkbox" id="subscribe">
                    Subscribe to newsletter</label>
                </div>
                <input type="submit" value="Send Now" class="btn3">
              </li>
            </ul>
          </form>
        </div>
      </div>
    </div>

    <jsp:include page="jsp/layout/footer.jsp"/>   
  </body>
</html>