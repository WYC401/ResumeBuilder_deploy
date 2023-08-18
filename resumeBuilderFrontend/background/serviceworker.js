// Define a variable to store the data received from the content script
var receivedData;

// Listen for messages from the content script
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
  if (request.message === "VariableData") {
    // Access the variable data received from the content script
    receivedData = request.data;
    
    // Use the receivedData variable in your background script or perform any other actions
    console.log(receivedData);
  }
});

window.receivedData = receivedData;
window.getReceivedData = function () {
  return receivedData;
};
