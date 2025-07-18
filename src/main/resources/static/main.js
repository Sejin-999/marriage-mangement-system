document.getElementById("enterBtn").onclick = function () {
    document.getElementById("loginModal").style.display = "block";
};

document.querySelector(".close").onclick = function () {
    document.getElementById("loginModal").style.display = "none";
};

document.getElementById("loginBtn").onclick = function () {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch(`/api/weddings/auth?username=${username}&password=${password}`, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                window.location.href = `/dashboard.html?username=${username}`;
            } else {
                document.getElementById("loginError").innerText = "아이디 또는 비밀번호 오류";
            }
        });
};
