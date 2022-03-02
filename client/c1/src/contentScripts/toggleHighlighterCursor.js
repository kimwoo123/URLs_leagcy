function toggle() {
  window.showHighlighterCursor = !window.showHighlighterCursor; // toggle
  if (window.showHighlighterCursor) {
    document.body.style.cursor = `url(${chrome.extension.getURL(
      'images/cursor.png',
    )}), auto`;
  } else {
    document.body.style.cursor = 'default';
  }
}

toggle();
