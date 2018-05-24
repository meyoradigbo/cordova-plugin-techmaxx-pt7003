# cordova-plugin-techmaxx-pt7003
Apache Cordova Printer Plugin for TechMaxx PT7003

## Supported Platforms
TechMaxx PT7003 POS
(printer7003.jar)

## Installation

```
cordova plugin add https://github.com/meyoradigbo/cordova-plugin-techmaxx-pt7003
```

# Sample Code

Initialise the reader manager and filters using initialise:

```
cordova.plugins.TechMaxxPT7003CordovaPlugin.initialise()
```

```

Clean up resources when our app exits:
```
window.onbeforeunload = function () {
	cordova.plugins.TechMaxxPT7003CordovaPlugin.destroy(function () { });
}
```

(function () {
    "use strict";

    document.addEventListener('deviceready', onDeviceReady.bind(this), false);

    function append(message) {
        var node = document.createElement("p");
        node.appendChild(document.createTextNode(message));
        document.body.appendChild(node);
    }

    async function onDeviceReady() {
        // Handle the Cordova pause and resume events
        document.addEventListener( 'pause', onPause.bind( this ), false );
        document.addEventListener('resume', onResume.bind(this), false);

        // TODO: Cordova has been loaded. Perform any initialization that requires Cordova here.
        await cordova.plugins.TechMaxxPT7003CordovaPlugin.initialise(async function () {

            append("init done");            

        });

        window.onbeforeunload = function () {
            cordova.plugins.TechMaxxPT7003CordovaPlugin.destroy(function () { });
        }

        append("setup done");
    };

    function onPause() {
        // TODO: This application has been suspended. Save application state here.
    };

    function onResume() {
        // TODO: This application has been reactivated. Restore application state here.
    };
} )();
```
