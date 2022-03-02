// 컨텐트 스크립트는 글로벌에서 사용하기 떄문에 오류가 난다.
function saveHighlight() {
  const selection = window.getSelection();
  const selectionString = selection.toString();

  if (selectionString) {
    const container = selection.getRangeAt(0).commonAncestorContainer;

    chrome.storage.sync.get('color', values => {
      const {color} = values;
      store(
        selection,
        container,
        window.location.href,
        color,
        highlightIndex => {
          highlight(
            selectionString,
            container,
            selection,
            color,
            highlightIndex,
          );
        },
      );
    });
  }
}
saveHighlight();
