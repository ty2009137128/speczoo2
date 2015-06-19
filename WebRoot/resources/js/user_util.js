function checkUserName(username) {
    var flag = true;

    if (username == "") {
        alert("Please enter user name!");
        flag = false;
    } else if (username.length < 1) {
        alert("The length of user name should be longer than 1!");
        flag = false;
    }

    return flag;
}

function checkPassword(psw) {
    var flag = true;

    if (psw == "") {
        alert("Please enter password!");
        flag = false;
    } else if (psw.length < 3) {
        alert("The length of password should not be less than 3!");
        flag = false;
    }

    return flag;
}

function checkPswConfirmPsw(psw, confirmPsw) {
    var flag = true;

    if (!checkPassword(psw)) {
        flag = false;
    } else if (confirmPsw == "") {
        alert("Please enter the confirm password!");
        flag = false;
    } else if (psw != confirmPsw) {
        alert("The password and confirm password are not the same!");
        flag = false;
    }

    return flag;
}

