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
                return response.json();  // ✅ weddingId가 숫자로 리턴됨
            } else {
                throw new Error("인증 실패");
            }
        })
        .then(weddingId => {
            window.location.href = `/dashboard.html?weddingId=${weddingId}`; // 🔁 weddingId 전달
        })
        .catch(() => {
            document.getElementById("loginError").innerText = "아이디 또는 비밀번호 오류";
        });
};
