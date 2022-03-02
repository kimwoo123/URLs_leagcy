$(() => {
  loadAll(window.location.href, window.location.pathname);
});

document.addEventListener('mouseup', () => {
  if (!window.showHighlighterCursor) return;

  const selection = window.getSelection();
  const selectionString = selection.toString();

  if (selectionString) {
    chrome.runtime.sendMessage({action: 'highlight'});
  }
});
