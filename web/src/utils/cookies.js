function saveAuthToCookie(value) {
	document.cookie = `til_auth=${value}`;
}

function saveUserToCookie(value) {
	document.cookie = `til_user=${value}`;
}

function saveUserEmailToCookie(value) {
	document.cookie = `til_user_email=${value}`;
}

function saveUseridToCookie(value) {
	document.cookie = `til_userid=${value}`;
}

function saveUserAvatarToCookie(value) {
	document.cookie = `til_avatar=${value}`;
}

function getAuthFromCookie() {
	return document.cookie.replace(
		/(?:(?:^|.*;\s*)til_auth\s*=\s*([^;]*).*$)|^.*$/,
		'$1',
	);
}

function getUserFromCookie() {
	return document.cookie.replace(
		/(?:(?:^|.*;\s*)til_user\s*=\s*([^;]*).*$)|^.*$/,
		'$1',
	);
}

function getUserEmailFromCookie() {
	return document.cookie.replace(
		/(?:(?:^|.*;\s*)til_user_email\s*=\s*([^;]*).*$)|^.*$/,
		'$1',
	);
}

function getUseridFromCookie() {
	return document.cookie.replace(
		/(?:(?:^|.*;\s*)til_userid\s*=\s*([^;]*).*$)|^.*$/,
		'$1',
	);
}

function getUserAvatarFromCookie() {
	return document.cookie.replace(
		/(?:(?:^|.*;\s*)til_avatar\s*=\s*([^;]*).*$)|^.*$/,
		'$1',
	);
}

function deleteCookie(value) {
	document.cookie = `${value}=; expires=Thu, 01 Jan 1999 00:00:10 GMT;`;
}

export {
	saveAuthToCookie,
	saveUserToCookie,
	saveUserEmailToCookie,
	saveUseridToCookie,
	saveUserAvatarToCookie,
	getAuthFromCookie,
	getUserFromCookie,
	getUserEmailFromCookie,
	getUseridFromCookie,
	getUserAvatarFromCookie,
	deleteCookie,
};