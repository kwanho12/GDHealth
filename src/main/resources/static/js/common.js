// 공통 로직
$(document).ajaxError(function(event, xhr, settings, thrownError) {
    if (xhr.status === 401) {
        window.location.href = "/employee/login";
    } else if (xhr.status === 403) {
        alert("권한이 없습니다.");
    } else if (xhr.status >= 500) {
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
});
