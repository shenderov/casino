export const errorHttp = data => {
    const error = JSON.parse(data.body);
    document.getElementById('error-modal').style.display = 'block';
    document.getElementById('error-modal-title').innerText = 'Status: ' + error.status + ', Path: ' + error.path;
    document.getElementById('error-modal-message').innerText = error.message;
    document.getElementById('error-modal-error').innerText = error.error;
    document.getElementById('error-modal-trace').innerText = error.trace;
    console.error(data);
}

export const errorMessage = data => {
    document.getElementById('error-modal').style.display = 'block';
    document.getElementById('error-modal-title').innerText = data.title;
    document.getElementById('error-modal-message').innerText = data.message;
    document.getElementById('error-modal-error').previousElementSibling.style.display = 'none';
    document.getElementById('error-modal-trace').style.display = 'none';
    document.getElementById('error-modal-show-trace').style.display = 'none';
}
