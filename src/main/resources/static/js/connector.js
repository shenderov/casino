export const httpGetCb = (url, error, callback) => {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url);
    xhr.withCredentials = true;
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 || (xhr.status === 0 && xhr.responseText !== '')) {
                callback({
                    url: url,
                    status: 200,
                    body: xhr.responseText || ''
                });
            } else {
                error({
                    url: url,
                    status: xhr.status,
                    body: xhr.responseText || ''
                });
            }
        }
    };
    xhr.send();
}

export const httpPost = (url, requestBody, error, callback) => {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.withCredentials = true;
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 || (xhr.status === 0 && xhr.responseText !== '')) {
                callback({
                    url: url,
                    status: 200,
                    body: xhr.responseText || ''
                });
            } else {
                error({
                    url: url,
                    status: xhr.status,
                    body: xhr.responseText || ''
                });
            }
        }
    };
    xhr.send(JSON.stringify(requestBody));
}