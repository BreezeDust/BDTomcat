/***
*   JBreeze V1.0
*   Copyright (C) 2013 BreezeDust(Yang Li) 
*   Emial:BreezeDust@qq.com
*   BreezeDust's webSite:http://www.breezedist.com
*   JBreeze :http://JBreeze.breezedust.com
*   Other Contributors; Licensed MIT
*
*   Permission is hereby granted, free of charge, to any person obtaining a copy of this 
*   software and associated documentation files (the "Software"), to deal in the Software 
*   without restriction, including without limitation the rights to use, copy, modify, merge, 
*   publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons 
*   to whom the Software is furnished to do so, subject to the following conditions:
*   The above copyright notice and this permission notice shall be included in all copies or substantial 
*   portions of the Software.
*   T  HE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
*   LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT 
*   SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION 
*   OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
*   IN THE SOFTWARE.
*/
(function (window) {
    var _JBreeze = {};
    var JBnumID = 1000;

    /**
    *@description  构造函数
    */
    var JBreeze = window.JBreeze = window.JB = function (idName) {
        var JBreezeDom = function () {
            this._name = "JB$" + idName;
            var htmlDom = document.getElementById(idName);

            this._init(htmlDom);
        };
        JBreezeDom.prototype = _JBreeze.father;
        return new JBreezeDom();
    };


    /**
    *@description  基类
    */
    _JBreeze.father = {
        /** 
        * @description  _init()
        * @param {object String} objStr_Obj 字符串或者HTML DOM对象
        * @param {className}  class名
        * 
        */
        _init: function (objStr_Obj, className) {
            //初始化事件管理器
            this._event = {};
            this._event["onclick"] = new Array();
            this._event["ondblclick"] = new Array();
            this._event["onmousedown"] = new Array();
            this._event["onmousemove"] = new Array();
            this._event["onmouseup"] = new Array();
            this._event["onmouseout"] = new Array();
            this._event["onmouseover"] = new Array();
            this._event["onkeydown"] = new Array();
            this._event["onkeypress"] = new Array();
            this._event["onkeyup"] = new Array();
            this._event["onload"] = new Array();

            
            //创建objt，设置className
            if (typeof objStr_Obj == "string") {
                this.dom = document.createElement(objStr_Obj);
            }
            else {
                this.dom = objStr_Obj;
            }
            this.setClassName(className);
            this.setID();

            //挂载事件
            this._InitEvent("onclick");
            this._InitEvent("ondblclick");
            this._InitEvent("onmousedown");
            this._InitEvent("onmousemove");
            this._InitEvent("onmouseup");
            this._InitEvent("onmouseout");
            this._InitEvent("onmouseover");
            this._InitEvent("onkeydown");
            this._InitEvent("onkeypress");
            this._InitEvent("onkeyup");
            this._InitEvent("onload");
            
        },
        _InitEvent: function (eventName) {
            var JBDomOption = this;
            this.dom[eventName] = function () {
                for (var con = 0; con < JBDomOption._event[eventName].length; con++) {
                    if (JBDomOption._event[eventName][con]) JBDomOption._event[eventName][con]();//运行被委托方法
                }
            }
        },
        setID: function (idStr) {
            if (!this.dom.id) this.dom.id = this.ID = "JB" + (JBnumID++);
            if (idStr) this.dom.id = this.ID = idStr;
        },
        getID: function () {
            return this.ID;
        },
        setSize: function (width, height) {
            if (typeof width == "string") {
                this.dom.style.width = width;
            }
            else {
                this.dom.style.width = width + "px";
            }
            if (typeof height == "string") {
                this.dom.style.height = height;
            }
            else {
                this.dom.style.height = height + "px";
            }

        },
        setWidth: function (width) {
            if (typeof width == "string") {
                this.dom.style.width = width;
            }
            else {
                this.dom.style.width = width + "px";
            }
        },
        setHeight: function (height) {
            if (typeof height == "string") {
                this.dom.style.height = height;
            }
            else {
                this.dom.style.height = height + "px";
            }
        },
        getWidth: function () {
            return JBreeze.getCSSValue(this.dom, "width");
        },
        getHeight: function () {
            return JBreeze.getCSSValue(this.dom, "height");
        },
        setPositionXY: function (Left, Top) {
            if (!this.getCss("position") || this.getCss("position") == "static") this.setCss({ "position": "relative" });
            if (typeof Left != "string") Left += "px";
            if (typeof Top != "string") Top += "px";
            this.setCss({ left: Left + "" });
            this.setCss({ top: Top + "" });
        },
        setPositionX: function (Left) {
            if (!this.getCss("position") || this.getCss("position") == "static") this.setCss({ "position": "relative" });
            if (typeof Left != "string") Left += "px";
            this.setCss({ left: Left + "" });
        },
        setPositionY: function (Top) {
            if (!this.getCss("position") || this.getCss("position") == "static") this.setCss({ "position": "relative" });
            if (typeof Top != "string") Top += "px";
            this.setCss({ top: Top + "" });
        },
        setClassName: function (className) {
            if (className) {
                this.dom.className = className;
            }
            else {
                this.dom.className = this._name;
            }
        },
        getClassName: function () {
            return this.dom.className;
        },
        setCss: function (cssObj) {
            if (typeof cssObj == "string") {
                if (document.all) this.dom.style.cssText = cssObj;
                else this.dom.setAttribute("style", cssObj);
            }
            else {
                for (var nameStr in cssObj) {
                    this.dom.style[JBreeze.cssToStyle(nameStr)] = cssObj[nameStr];
                }
            }

        },
        getCss: function (cssName) {
            if (!cssName) return this.dom.getAttribute("style");
            return JBreeze.getCSSValue(this.dom, cssName);
        },
        innerHTML: function (htmlStr) {
            this.dom.innerHTML = htmlStr;
        },
        //添加JBreeze对象到父对象
        append: function (JBDom) {
            var newName = JBDom._name + "S";
            //为子对象建立索引
            if (!this[newName]) {
                this[newName] = new Array();
            }
            this[newName][this[newName].length] = JBDom;

            //原生dom的添加
            this.dom.appendChild(JBDom.dom);
        },
        addBefore: function (JBDom,toJBDom) {
            var newName = JBDom._name + "S";
            //为子对象建立索引
            if (!this[newName]) {
                this[newName] = new Array();
            }
            this[newName][this[newName].length] = JBDom;

            //原生dom的添加
            this.dom.insertBefore(JBDom.dom, toJBDom.dom);
        },
        removeAll: function () {
            this.innerHTML("");
            var zzStr = /[J][B]/;
            for (var name in this) {
                if (zzStr.test(name)) {
                    this[name] = null;
                    delete (this[name]);
                }
            }
        },
        removeThis: function (JBDom) {
            if (this[JBDom._name + "s"]) {
                for (var con = 0; con < this[JBDom._name + "s"].length; con++) {
                    if (this[JBDom._name + "s"][con] === JBDom) {
                        this.dom.removeChild(JBDom.dom);
                        this[JBDom._name + "s"].splice(con, 1);
                        if (this[JBDom._name + "s"].length == 0) delete (this[JBDom._name + "s"]);
                        return true;
                    }
                }
                return false;
            }
            return false;

        },
        onclick: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onclick", funOption, clearEvent);
        },
        ondblclick: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "ondblclick", funOption, clearEvent);
        },
        onmousedown: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onmousedown", funOption, clearEvent);
        },
        onmousemove: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onmousemove", funOption, clearEvent);
        },
        onmouseup: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onmouseup", funOption, clearEvent);
        },
        onmouseout: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onmouseout", funOption, clearEvent);
        },
        onmouseover: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onmouseover", funOption, clearEvent);
        },
        onkeydown: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onkeydown", funOption, clearEvent);
        },
        onkeypress: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onkeypress", funOption, clearEvent);
        },
        onkeyup: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onkeyup", funOption, clearEvent);
        },
        onload: function (funOption, clearEvent) {
            JBreeze._eventManage(this, "onload", funOption, clearEvent);
        },
        /***
        *------------Effect----------------
        */
        /** 
        *
        * @description  阴影效果
        * @param {String} color 颜色 有默认值
        * @param {Int} radius 阴影半径 有默认值
        */
        EF_isShaDow: function (color, radius) {
            if (!color) color = "#333333";
            if (!radius) radius = 4;
            var SDstr = "-moz-box-shadow: "+(radius-1)+"px "+(radius-1)+"px "+(radius)+"px " + color + ";-webkit-box-shadow: "+(radius-1)+"px "+(radius-1)+"px "+(radius)+"px " + color + ";box-shadow: "+(radius-1)+"px "+(radius-1)+"px "+(radius)+"px " + color + ";-ms-filter: \"progid:DXImageTransform.Microsoft.Shadow(Strength="+(radius)+", Direction=135, Color='" + color + "')\";filter: progid:DXImageTransform.Microsoft.Shadow(Strength="+(radius)+", Direction=135, Color='" + color + "');";
            var exStr = /[;]$/;
            var str = this.getCss();
            if (str && !exStr.test(str)) str += ";";
            str += SDstr;
            this.setCss(str);
        },
        /** 
        *
        * @description  拖动效果
        * @param {Boolean} isCanMove true 为可拖动 有默认值
        * @param {Boolean} fatherDeep true 为父级对象拖动 有默认值
        */
        EF_baseMove: function (isCanMove, fatherDeep) {
        if (isCanMove) {
            JBreeze._canMove(this, fatherDeep)
        }
        else {
            this.onmousemove(null, true);
            this.onmousedown(null, true);
            this.onmouseup(null, true);
        }
        },
        /** 
        *
        *必须设置长宽，次效果才有效
        * @description  水平居中
        * 
        */
        EF_XCenter: function () {
            this.setCss({  position:"relative",margin:"0px auto"});
        },
        /** 
        *
        *必须设置长宽，次效果才有效
        * @description  水平垂直居中
        * @param {Boolean} isFixed true 为屏幕中间不随页面滚动 有默认值
        */

        EF_XYCenter: function (isFixed) {
            if (!isFixed) isFixed = "absolute";
            else isFixed = "fixed";
            var width = parseInt(this.getWidth()+0)/2;
            var height = parseInt(this.getHeight() + 0) / 2;
            var SDstr = "position:"+isFixed+";left:50%;top:50%;margin-left:-" + width + "px;margin-top:-" + height + "px;";
            var exStr = /[;]$/;
            var str = this.getCss();
            if (str && !exStr.test(str)) str += ";";
            str += SDstr;
            this.setCss(str);
        },
        /***
        *仅用于<body>, <frame>, <frameset>, <iframe>, <img>, <link>, <script>
        * @description  图像等加载效果
        *@param {String} html ineerHtml
        *
        */
        EF_loading: function (html) {

            if (!html) html = "loading....";
            var loading = new JBreeze.OBJ("Loading", "div", html, "load");
            var parents = this.dom.parentNode;
            parents.insertBefore(loading.dom, this.dom);
            loading.setCss("background-color:#425453;color:#F5FFFE;font-size:13px;width:60%;height:30px;line-height:30px;padding-left:6px;");
            var JBobj = this;
            this.onload(function () {
                parents.removeChild(loading.dom);
                JBobj.onload(null, arguments.callee);
            });
            
        },
        


    };
    /** 
    *{ className : class名称  , cmdStr : html名称 ,  html : innerHTML , name : 名字}
    * @description  JBreeze.OBJ()
    * @param {String} className class样式
    * @param {String} cmdStr html名称
    * @param {String} html innerHTML内容
    * @param {String} name 自定义名字
    */
    JBreeze.OBJ = _JBreeze.OBJ = function (className, cmdStr, html, name) {
        //参数初始化
        if (!cmdStr) cmdStr = "div";
        if (!name) name = "";

        this._name = "JB" + cmdStr + "_" + name;

        this._init(cmdStr, className);
        var tempDom = this.dom;
        if (html) this.innerHTML(html);

    };
    //继承父对象
    JBreeze.OBJ.prototype = _JBreeze.OBJ.prototype = _JBreeze.father;



    /** 
    * @description  JBreeze 可视化类库
    */
    JBreeze.GUI = {
        /** 
        *{className ：class名称 , haveTitle : 是否有标题栏, title : JBtitle对象 , html : innerHTML文本}
        * @description  Frame
        * @param {String} className 
        * @param {Boolean} haveTitle 是否有标题栏
        * @param {Html} html  innerHTML文本
        */
        Frame: function (className, haveTitle, html) {
            //参数初始化
            this.JBTitle_ = null;
            if (typeof haveTitle == "undefined") haveTitle = true;
            if (haveTitle) this.JBTitle_ = new JBreeze.GUI.Title();

            this._name = "JBFrame";

            this._init("div", className);
            var tempDom = this.dom;
            document.body.appendChild(tempDom);


            if (html) this.innerHTML(html);
            if (haveTitle) this.append(this.JBTitle_);




        },
        /** 
        *{className : class名称, html : innerHTML文本}
        * @description  IFrame
        * @param {String} className class名称
        * @param {String} html  innerHTML文本
        * 
        */
        IFrame: function (className, html) {

        },
        /** 
        *{className : class名称, html : innerHTML文本}
        * @description  Panel
        * @param {String} className class名称
        * @param {String} html  innerHTML文本
        * 
        */
        Panel: function (className, html) {
            //参数初始化


            this._name = "JBPanel";

            this._init("div", className);
            var tempDom = this.dom;

            if (html) this.innerHTML(html);
        },
        /** 
        *{className ： class名称 ,titleName : 标题名, close : 关闭按钮 ,html : innerHTML文本}
        * @description  Title
        * @param {String} className class样式
        * @param {String} titleName 标题名 
        * @param {Object} close 关闭按钮
        * @param {String} html innerHTML文本
        */
        Title: function (className, titleName, close, html) {
            //参数初始化
            this.JBtitleLabel_ = null;
            this.JBclose_ = null;

            if (!titleName) titleName = "";
            if (!close) close = new JBreeze.OBJ("JBTitle_close", null, "X", "Close");

            this._name = "JBTitle";

            this._init("div", className);
            var tempDom = this.dom;


            if (html) this.innerHTML(html);

            this.JBtitleLabel_ = new JBreeze.GUI.Label(null, titleName);
            this.append(this.JBtitleLabel_);

            this.JBclose_ = close;
            this.append(this.JBclose_);

            var rootObj = this.dom;

            this.JBclose_.onclick(function () {
                rootObj.parentNode.parentNode.removeChild(rootObj.parentNode);
            });

         
            //[Function]
            /** 
            *
            * @description  setTitleName()
            * @param {String} nameStr 标题名
            */
            this.setTitleName = function (nameStr) {
                this.JBtitleLabel_.SetTextValue(nameStr);
            };

            //[Event]
            this.onClose = function (funOption, clearEvent) {
                this.JBclose_.onclick(funOption, clearEvent);
            };

        },
        /** 
        *
        *{ className ： class名称 ,texts : 内容 }
        * @description  Label
        * @param {String} className class样式
        * @param {String} texts 内容
        * 
        */
        Label: function (className, texts) {
            //参数初始化

            if (!texts) texts = "New Label";

            this._name = "JBLabel";

            this._init("div", className);
            var tempDom = this.dom;

            tempDom.innerHTML = texts;
            this.setClassName(className);

            //[method]
            this.setFontSize = function (size) {
                tempDom.style.fontSize = size + "px";
            }
            this.SetTextValue = function (textSrt) {
                this.innerHTML(textSrt);
            }
        },
        /** 
        *{ className ： class名称 }
        * @description  Text
        * @param {String} className class样式
        */
        Text: function (className,nameStr) {
            //参数初始化
            this.JBlabeName_ = null;
            this.JBinput_ = null;
            if(!nameStr) nameStr="";
            this._name = "JBText";

            this._init("div", className);

            this.JBlabeName_ = new JBreeze.GUI.Label(null, nameStr);
            this.JBinput_ = new JBreeze.OBJ(null, "input", null);
            this.JBinput_.dom.setAttribute("type", "text");
            this.append(this.JBlabeName_);
            this.append(this.JBinput_);
            var tempDom = this.dom;
            //[Function]
            
            
        },
        /** 
        * { className ： class名称 , name : 按钮名称  }
        * @description  Button
        * @param {String} className class样式
        * @param {String} name  按钮名称  
        */
        Button: function (className, name) {
            //参数初始化
            if (!name) name = "Button";

            this._name = "JBButtont";

            this._init("input", className);
            var tempDom = this.dom;
            tempDom.setAttribute("type", "button");
            tempDom.setAttribute("value", name);
        },
        /** 
        *{ className ： class名称 , titleName : 标题名 , bodyText: 正文  ,html : html文本}
        * @description  MSG
        * @param {String} className class样式
        * @param {String} titleName  标题名
        * @param {String} bodyText  正文
        * @param {html} html  html文本
        */
        MSG: function (className, titleName, bodyText, html) {
            //参数初始化
            this.JBButton1_ = null;
            this.JBButton2_ = null;
            this.JBTitle_ = null;
            this.JBbodyLable_ = null;
            if (!titleName) titleName = "";
            if (!bodyText) bodyText = "";

            var Fm = new JBreeze.GUI.Frame(className);
            var bt1 = new JBreeze.GUI.Button(null, "确定");
            var bt2 = new JBreeze.GUI.Button(null, "取消");
            var la1 = new JBreeze.GUI.Label(null, bodyText);
            la1.setSize(300, 20);
            la1.setCss({ "text-align": "center" });
            Fm.JBTitleS[0].JBLabelS[0].setFontSize(11);
            Fm.JBTitleS[0].JBLabelS[0].SetTextValue(titleName);
            Fm.JBTitleS[0].JBdiv_CloseS[0].setCss({ fontSize: "11px" });
            bt1.setPositionXY("30px", "100px");
            bt2.setPositionXY("60px", "100px");
            //bt2.setPositionXY();
            Fm.setSize(300, 185);
            Fm.append(la1);
            Fm.append(bt1);
            Fm.append(bt2);
            this.JBbodyLable_ = la1;
            this.JBButton1_ = bt1;
            this.JBButton2_ = bt2;
            this.JBTitle_ = Fm.JBTitle_;
            if (html) this.innerHTML(html);

            //[Event]
            Fm.onConfirm = function (funOption, clearEvent) {
                bt1.onclick(funOption, clearEvent);
            };
            Fm.onNegative = function (funOption, clearEvent) {
                bt2.onclick(funOption, clearEvent);
            };
            return Fm;

        },
        /** 
        *{ className ： class名称 , nameArray: 名字数组[名字1，名字2...],hrefArray : 连接数组 [连接1,连接2],html : innerHTM文本}
        * @description  navigationBar
        * @param {String} className class样式
        * @param {Array} nameArray  名字数组 [名字1，名字2...]
        * @param {Array} hrefArray  连接数组 [连接1, 连接2...]
        * @param {html} html  html文本
        */
        NavigationBar: function (className, nameArray, hrefArray, isStand,CleanStyle,html) {
            //参数初始化
            this.JBbarS_ = null;
            this.JBbar_ = null;
            
            if (CleanStyle) CleanStyle = "";
            else isChildStyle = "JBNavigationBarA";
            if (!nameArray) nameArray = new Array();
            if (!hrefArray) hrefArray = new Array();

            this._name = "JBNavigationBar";

            this._init("div", className);
            var tempDom = this.dom;
            //document.body.appendChild(tempDom);

            this.JBbar_ = new JBreeze.OBJ(null, "div", null, "Bar");
            this.append(this.JBbar_);
            this.JBbarS_ = new Array();
            for (var con = 0; con < nameArray.length; con++) {
                this.JBbarS_[con] = new JBreeze.OBJ(CleanStyle, "a", null, "A");
                this.JBbar_.append(this.JBbarS_[con]);
                if (nameArray[con]) this.JBbarS_[con].innerHTML(nameArray[con]);
                if (hrefArray[con]) this.JBbarS_[con].dom.setAttribute("href", hrefArray[con]);

            }
            if (html) this.innerHTML(html);



            //[Function]

            this.isStand = function (stand) {
                if (stand) {
                    for (var con = 0; con < this.JBbarS_.length; con++) {
                        if (document.all) this.JBbarS_[con].dom.style.styleFloat = "none";
                        else this.JBbarS_[con].dom.style.cssFloat = "none";
                    }

                }
                else {
                    for (var con = 0; con < this.JBbarS_.length; con++) {
                        if (document.all) this.JBbarS_[con].dom.style.styleFloat = "left";
                        else this.JBbarS_[con].dom.style.cssFloat = "left";
                    }
                }
            }

            if (isStand) this.isStand(true);


        },
    };

    //为GUI的每个类挂载father
    for (var objName in JBreeze.GUI) {
        JBreeze.GUI[objName].prototype = _JBreeze.father;
    }

    /***
    *-----------------------System------------------------------------
    */

    /** 
    * @description  负于JB对象拖动功能
    * @param {object} JBobj JB对象
    * @param {int} fatherDeep 向父级延生的深度
    */
    JBreeze._canMove = function (JBobj, fatherDeep) {
        var tempDom = JBobj.dom;
        var toDom = JBobj.dom;

        //[add move event]拖动功能
        var Mousedown = false;
        var eX = 0;
        var eY = 0;
        var oldLeft = 0;
        var oldTop = 0;
        //var zindex;
        JBobj.onmousedown(
            function () {
                //在事件发生时检查父对象，因为中间可能变化，或者不存在
                if (fatherDeep) toDom = JBobj.dom.parentNode;

                eX = event.clientX;
                eY = event.clientY;
                oldLeft = toDom.offsetLeft;
                oldTop = toDom.offsetTop;

                toDom.style.position = "absolute";
                toDom.style.posLeft = oldLeft + event.clientX - eX;
                toDom.style.posTop = oldTop + event.clientY - eY;

                //zindex = toDom.style.zIndex;
                //toDom.style.zIndex = 100;
                tempDom.style.cursor = "move";

                Mousedown = true;

            }
            );
        JBobj.onmousemove(
             function () {
                 if (Mousedown) {
                     var sx = oldLeft + event.clientX - eX;
                     var sy = oldTop + event.clientY - eY;
                     toDom.style.posLeft = sx;
                     toDom.style.posTop = sy;
                 }
             }
            );

        JBobj.onmouseup(
           function () {
               Mousedown = false;
               tempDom.style.cursor = "default";
               //toDom.style.zIndex = zindex;
           }
           );
        //[add move event] end
    };

    /***
    *-------------------------Event------------------------------
    */
    /** 
    * @description  扩展模块对象
    * @param {object} objEx 模块
    */
    JBreeze.extendOBJ = function (objEx) {
        for (var name in objEx) {
            JBreeze.GUI[name] = objEx[name];
            JBreeze.GUI[name].prototype = _JBreeze.father;
        }
    };

    _JBreeze._clearFun = function () { };//用于标记无用事件，用于安全处理
    /** 
    * @description  事件注册管理函数
    * @param {object} JBobj JB对象
    * @param {String} eventName 事件名字
    * @param {Function} funOption 函数指针
    * @param {Boolean} clearEvent 是否清除以前的事件响应函数
    */
    JBreeze._eventManage = function (JBobj, eventName, funOption, clearEvent) {
        if (clearEvent) {
            if (typeof clearEvent == "function") {
                for (var con = 0; con < JBobj._event[eventName].length; con++) {
                    if (JBobj._event[eventName][con] == clearEvent) {
                        //JBobj._event[eventName].splice(con, 1);//移除事件监听函数
                        JBobj._event[eventName][con] = _JBreeze._clearFun;//移除事件监听函数 安全模式
                    }
                }
            }
            else {
                JBobj._event[eventName] = new Array();//为BOOL值时，直接清除事件
            }

        };
        if (funOption) JBobj._event[eventName][JBobj._event[eventName].length] = funOption;
    };

    //事件管理器
    JBreeze._event = {};
    JBreeze._event["JB_onReady"] = new Array();
    //ready事件挂载函数
    JBreeze._JB_onReady = function () {
        if (document.addEventListener) {//FF 
            document.addEventListener("DOMContentLoaded", function () {
                //注销事件，避免反复触发  
                document.removeEventListener("DOMContentLoaded", arguments.callee, false);
                for (var con = 0; con < JBreeze._event["JB_onReady"].length; con++) {
                    if (JBreeze._event["JB_onReady"][con]) JBreeze._event["JB_onReady"][con]();//运行被委托方法
                }

            }, false);
        }

        if (document.attachEvent) {//IE  
            document.attachEvent("onreadystatechange", function () {
                if (document.readyState === "complete") {
                    document.detachEvent("onreadystatechange", arguments.callee);
                    for (var con = 0; con < JBreeze._event["JB_onReady"].length; con++) {
                        if (JBreeze._event["JB_onReady"][con]) JBreeze._event["JB_onReady"][con]();//运行被委托方法
                    }
                }
            });
        }
    };
    JBreeze._JB_onReady();
    /** 
    * @description  JB ready事件
    * @param {function} funOption 函数指针
    * @param {Boolean} clearEvent 是否清除以前的事件响应函数
    */
    JBreeze.onReady = function (funOption, clearEvent) {
        JBreeze._eventManage(JBreeze, "JB_onReady", funOption, clearEvent);
    };
    /***
    *-------------------------全局对象------------------------------
    */

    //创建JB BODY对象
    JBreeze.JBBody = function () {
        this._name = "JBBoy";
        this.dom = document.body;
    };
    JBreeze.JBBody.prototype = _JBreeze.father;
    JBreeze.onReady(function () {
        JBreeze.JBBody = new JBreeze.JBBody();
        JBreeze.onReady(null, arguments.callee);
    });
    

    /***
    *-------------------------CSS------------------------------
    */

    JBreeze.trim = function (str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    };
    /** 
    * @description  将驼峰式转换成CSS样式
    * @param {String} StyleStr 驼峰式 
    */
    JBreeze.styleToCss = function (StyleStr) {
        return StyleStr.replace(/([A-Z])/g, "-$1").toLowerCase();
    };
    /** 
    * @description  将CSS样式写法转换成驼峰式
    * @param {String} cssStr CSS样式写法
    */
    JBreeze.cssToStyle = function (cssStr) {
        var strNum = cssStr.split("-");
        var newStr = strNum[0];
        for (var con = 1; con < strNum.length; con++) {
            newStr += strNum[con].slice(0, 1).toUpperCase() + strNum[con].slice(1);
        }
        return newStr;
    };
    /** 
    * @description  得到CSS的值
    * @param {Object} obj HTML DOM
    * @param {String} nameStr 驼峰式或者CSS样式
    */
    JBreeze.getCSSValue = function (obj, nameStr) {

        //style
        var styleStr = JBreeze.cssToStyle(nameStr);
        var cssStr = JBreeze.styleToCss(nameStr);
        if (obj.style[styleStr]) {
            return obj.style[styleStr];
        }
            //IE CSS
        else if (obj.currentStyle) {

            return obj.currentStyle[styleStr];
        }
            //FF CSS
        else if (document.defaultView && document.defaultView.getComputedStyle) {
            var style = document.defaultView.getComputedStyle(obj, null);
            return style.getPropertyValue(cssStr);
        }
        return null;

    };

    /***
    *-------------------------AJAX------------------------------
    */

    /** 
    * @description  JB HttpRequest对象
    * 
    */
    JBreeze.getHttpRequest = function () {
        var createXHR;
        if (window.XMLHttpRequest) {
            // IE7+, Firefox, Chrome, Opera, Safari
            createXHR = new XMLHttpRequest();
        }
        else {
            // c IE6, IE5
            createXHR = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return createXHR;
    };
    /** 
    * @description  GET方法请求
    * @param {String} url 请求地址
    * @param {Boolean} isXML 是否是XML
    * @param {function} callback(data) 成功时要执行的函数
    */
    JBreeze.Get = function (url, isXML, callback) {
        var JBGEThttp = JBreeze.getHttpRequest();
        var getData = "";
        JBGEThttp.onreadystatechange = function () {
            if (JBGEThttp.readyState == 4 && JBGEThttp.status == 200) {
                if (!isXML) getData = eval("(" + JBGEThttp.responseText + ")");
                if (isXML) getData = JBGEThttp.responseXML;

                if (callback) callback(getData);
            }
        }
        JBGEThttp.open("GET", url, true);
        JBGEThttp.send();

    };
    /** 
    * @description  POST方法请求
    * @param {String} url 请求地址
    * @param {Boolean} isXML 是否是XML
    * @param {Object} data 参数对象 {"名字":"值","名字":"值".....}
    * @param {function} callback(data) 成功时要执行的函数
    */
    JBreeze.Post = function (url, isXML, data, callback) {
        var dataStr = "";
        if (data) {
            for (var name in data) {
                dataStr += name + " = " + encodeURIComponent(data[name]) + "&";
            }
        }
        var JBPostHttp = JBreeze.getHttpRequest();
        var getData = "";
        JBPostHttp.onreadystatechange = function () {
            if (JBPostHttp.readyState == 4 && JBPostHttp.status == 200) {
                if (!isXML) getData = eval("(" + JBPostHttp.responseText + ")");
                if (isXML) getData = JBPostHttp.responseXML;

                if (callback) callback(getData);
            }
        }
        JBPostHttp.open("POST", url, true);
        JBPostHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        JBPostHttp.send(dataStr);
    };
    /** 
    * @description  Load方法加载远程数据
    * @param {String} url 请求地址
    * @param {function} callback(data) 成功时要执行的函数
    */
    JBreeze.Load = function (url, callback) {
        var JBGEThttp = JBreeze.getHttpRequest();
        var getData = "";
        JBGEThttp.onreadystatechange = function () {
            if (JBGEThttp.readyState == 4 && JBGEThttp.status == 200) {
                 getData = JBGEThttp.responseText;
                if (callback) callback(getData);
            }
        }
        JBGEThttp.open("GET", url, true);
        JBGEThttp.send();
    };
    /***
    *------------------------TOOL------------------------
    */
    /** 
    * @description  预加载图片
    * @param {Array} urlArry 图片地址数组
    */
    JBreeze.ImageLoad = function (urlArry) {
        for (var con = 0; con < urlArry.length; con++) {
            var img = new Image();
            img.src = urlArry[con];
        }
    }
})(window);