setTimeout(() => {
    console.log("Javascript: Hello World.");
    const bridge = window.WebViewJavascriptBridge;
    console.log("libo---");
    console.log(600 + "px")

    var osmd = new opensheetmusicdisplay.OpenSheetMusicDisplay("musicxml", {
        autoResize: true, // just an example for an option, no option is necessary.
        backend: "svg",
        drawTitle: true,
        // put further options here
    });
    // BrahWiMeSample.musicxml
    osmd
        .load("/storage/emulated/0/musicxml/BrookeWestSample.musicxml")
        .then(
            function () {
                osmd.render();
                //  document.getElementById("osmdCanvasPage1").setAttribute("width", "100%")
            }
        );


    document.getElementById("addfile").onclick = function () {
        // 设置在此处单击#button时要发生的事件
        console.log("i am be clicked\n");
        bridge.callHandler('chooseFile',{ key: 'JSValue' }, function (response) {
            console.log(response);
        });
    }



    // bridge.callHandler('DeviceLoadJavascriptSuccess', { key: 'JSValue' }, function (response) {
    //     let system = response.system
    //     let width = response.width
    //     let height = response.height
    //     console.log("ssss", response);
    //     console.log("ssss", system);
    //     console.log("ssss", width, height);
    //     if (system === "iOS") {
    //         console.log("Javascript was loaded by IOS and successfully loaded.");
    //         // document.getElementById("SDBridge").innerText = "Javascript was loaded by IOS and successfully loaded.";
    //         window.iOSLoadJSSuccess = true;
    //     } else if (system === "Android") {
    //         console.log("Javascript was loaded by Android and successfully loaded.");
    //         //  document.getElementById("SDBridge").innerText = "Javascript was loaded by Android and successfully loaded.";
    //         window.AndroidLoadJSSuccess = true;
    //     }
    //     // document.getElementById("musicMain").style.width = width / 2 + "px"
    //     // document.getElementById("musicMain").style.height = height / 2 + "px"
    // });

    // JS register method is called by native
     bridge.registerHandler('GetToken', function (data, responseCallback) {
         console.log(data);
         document.getElementById("SDBridge").innerText = "JS get native data:" + JSON.stringify(data);
         let result = { token: "I am javascript's token" }
         //JS gets the data and returns it to the native
         responseCallback(result)
     });
     bridge.registerHandler('AsyncCall', function (data, responseCallback) {
         console.log(data);
         document.getElementById("SDBridge").innerText = "JS get native data:" + JSON.stringify(data);
         // Call await function must with  (async () => { })();
         (async () => {
             const callback = await generatorLogNumber(1);
             let result = { token: callback };
             responseCallback(result);
         })();
     });
     bridge.registerHandler('OnChooseFile', function (data, responseCallback) {
          console.log("12345466477");
        //  var obj =JSON.parse(data);
         console.log(data.file)
         osmd
              .load(data.file)
              .then(
                  function () {
                      osmd.render();
                      //  document.getElementById("osmdCanvasPage1").setAttribute("width", "100%")
                  }
              );

//          (async () => {
//              const callback = await generatorLogNumber(1);
//              let result = { token: callback };
//              responseCallback(result);
//          })();
      });

    // function generatorLogNumber(n) {
    //     return new Promise(res => {
    //         setTimeout(() => {
    //             res("Javascript async/await callback Ok");
    //         }, 1000);
    //     })
    // }
}, 100);