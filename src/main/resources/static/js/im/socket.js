var fileData;
layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    //融云key
    //var rcKey = 'x4vkb1qpx41zk';
    var call = {};
    /*如果服务器没连接上，用户直接发送数据，会将消息保存到queue中，之后连接成功， 执行发送
     *注意:如果用户直接关掉浏览器，则消息丢失,可以通过消息记录的形式保存。
     *正常情况下会有一些连接服务器延迟，所以，用此方案即可，不必存放于localstorage中
     */
    var msgQueue = [];
    var userGroups = [];
    //事件监听
    var listener = function (code) {
        code && (call['status'] ? call['status'](code) : log(code));

    }
    //记录日志
    var log = function (msg) {
        conf.log && console.log(msg);
    }

    var conf = {
        uid: 0,//连接的用户id，必须传
        key: '',//融云key
        log: true,//是否开启日志
        userInfoUrl: '',//获取用户信息的接口
        layim: null,
        token: {
            url: '',
            uselocal: true
        },
        autoConnect: true,//自动连接
    };

    //这里引用融云，但是socket接口都是一致的
    //事件有很多 open
    var socket = {
        config: function (options) {
            conf = $.extend(conf, options);
            log('当前用户配置：');
            log(conf);
            //初始化im
            im.init();
            conf.autoConnect ? this.open() : listener(im.code.init);
            //封装关于layim的事件
            this.register();
        },
        register: function () {
            var layim = conf.layim;
            if (layim) {
                //监听发送消息
                layim.on('sendMessage', function (data) {
                    //调用socket方法，发送消息
                    log(data);
                    //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
                    im.sendMsgWithQueue(data);
                });

                layim.on('rollback', function (data) {
                    //推送到服务器
                    var msg = new RongIMLib.RecallCommandMessage({
                        messageUId: data.id,
                        conversationType: data.conversationType,
                        targetId: data.targetId,
                        sentTime: data.sentTime
                    });
                    log(msg);
                    RongIMClient.getInstance().sendMessage(data.conversationType, data.targetId, msg, {
                        onSuccess: function (message) {
                            log('发送消息成功');
                        },
                        onError: function (errorCode) {
                            log(errorCode);
                        }
                    }, null, null, null, '2');

                    //界面上提示
                    rollback(data.id, data.targetId, data.nickname, data.conversationType==1?"":"group");


                });

                layim.on('startVoIP', function (data) {
                    //调用socket方法，发送消息
                    log(data);
                    //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
                    targetId = data.targetId;
                    startConversation(RongIMLib.ConversationType.PRIVATE, targetId, [targetId], mediaVideo, "");
                });
                //监听ready事件
                layim.on('ready', function () {
                    log('初始化群组信息...');
                    userGroups = conf.layim.cache().group || [];
                });

                layim.on('chatChange', function (data) {
                    log('更新用户状态');
                    log(data);
                    if (data.data.type != "group") {
                        checkOnline(data.data.id);
                    } else {
                        //打开面板的时候直接显示群人数
                        var members = layim.cache().base.members || {};
                        members.data = $.extend(members.data, {
                            id: data.data.id
                        });

                        post(members, function (res) {
                            //获取群员
                            data.elem.find('.layim-chat-members').html(res.members || (res.list || []).length + '人');
                        });
                    }
                });

                layim.on('sign', function (value) {
                    console.log(value); //获得新的签名
                    var init = {
                        url: 'lay/user/sign' //接口地址（返回的数据格式见下文）
                        , type: 'post' //默认get，一般可不填
                        , data: {
                            uid: conf.uid,
                            sign: value
                        } //额外参数
                    };
                    post(init, function () {

                    });

                });
            }
        },
        open: function (uid) {
            uid = uid || conf.uid;
            if (uid == 0) {
                layer.msg('无用户id,请检查socket.config中uid参数');
                return;
            }
            im.connectWithToken();
        },
        on: function (event, callback) {
            call[event] ? '' : call[event] = callback;
        }
    };

    //检查在线状态
    var checkOnline = function (uid) {
        $.get(conf.checkOnline.url, {uid: uid}, function (res) {
            if (res.result.success) {
                if (res.online) {
                    conf.layim.setChatStatus('<span style="color:#FF5722;">在线</span>'); //模拟标注好友在线状态
                } else {
                    conf.layim.setChatStatus('<span style="color:#a7a7a7;">离线</span>'); //模拟标注好友在线状态
                }

            } else {
                layer.msg('token获取失败，请检查相应配置或者服务端代码');
            }
        });
    };


    var tryGetTokenTime = 0;//token重试次数
    var CallLib;
    //im部分
    var im = {
        connected: false,
        init: function () {
            //初始化融云设置
            log('初始化融云设置,key=' + conf.key);
            if (conf.key) {
                RongIMClient.init(conf.key);
                this.initListener();
                this.defineMessage();
                var options = {
                    container: {
                        local: document.getElementById("call") //local 为 DOM 节点
                        ,remote: document.getElementById("remote")
                    }
                };
                RongIMLib.RongIMEmoji.init();
                RongIMLib.RongIMVoice.init();
                // 初始化 WebCallLib
                RongIMLib.RongCallLib.init(options);
                CallLib = RongIMLib.RongCallLib.getInstance();
            } else {
                layer.msg('请检查config.key配置')
            }


        },
        code: {
            init: {code: 0, msg: '配置初始化，尚未连接'},
            success: {code: 10000, msg: '连接成功'},
            usuccess: {code: 10000, msg: '用户连接成功'},
            connecting: {code: 10001, msg: '正在连接'},
            disconnect: {code: 10002, msg: '断开连接'},
            errorNetwork: {code: 10003, msg: '网络不可用'},
            errorOtherLogin: {code: 10004, msg: '异地登录'},
            errorDomain: {code: 10005, msg: '域名不正确'},
            errorToken: {code: 10006, msg: 'token 无效'},
            errorTimeout: {code: 10007, msg: '超时'},
            errorUnKnown: {code: 10008, msg: '未知错误'},
            errorVersion: {code: 10009, msg: '不可接受的协议版本'},
            errorAppkey: {code: 10010, msg: 'appkey 无效'},
            errorService: {code: 10011, msg: '服务器不可用'}
        },
        getToken: function (uid, callback) {

            //从本地读取
            var t = token.get(uid);

            if (t && t.length > 10) {
                log('从本地获取token');
                callback(t);
                return;
            }
            if (!conf.token.url) {
                layer.msg('请检查config.token.url配置');
                return;
            }
            //根据网络请求获取token
            log("从网络获取token");
            $.get(conf.token.url, {uid: uid}, function (res) {
                if (res.token && callback) {
                    if (conf.token.uselocal) {
                        log('将token保存到本地');
                        token.save(res.token);
                    }
                    callback(res.token);
                } else {
                    layer.msg('token获取失败，请检查相应配置或者服务端代码');
                }
            });
        },
        connectWithToken: function () {
            im.getToken(conf.uid, function (t) {
                RongIMClient.connect(t, {
                    onSuccess: function (userId) {
                        im.connectSuccess(userId);
                    },
                    onTokenIncorrect: function () {
                        listener(im.code.errorToken);
                        //重新获取
                        if (tryGetTokenTime <= 4) {
                            log("正在重试");
                            token.reset();
                            im.connectWithToken();
                            tryGetTokenTime++;
                        } else {
                            layer.msg('获取token失败');
                        }

                    },
                    onError: function (errorCode) {
                        var info = '';
                        var code = im.code.errorUnKnown;
                        switch (errorCode) {
                            case RongIMLib.ErrorCode.TIMEOUT:
                                code = im.code.errorTimeout;
                                break;
                            case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                                code = im.code.errorUnKnown;
                                break;
                            case RongIMLib.ErrorCode.UNACCEPTABLE_PaROTOCOL_VERSION:
                                code = im.code.errorVersion;
                                break;
                            case RongIMLib.ErrorCode.IDENTIFIER_REJECTED:
                                code = im.code.errorAppkey;
                                break;
                            case RongIMLib.ErrorCode.SERVER_UNAVAILABLE:
                                code = im.code.errorService;
                                break;
                        }
                        listener(code);
                    }
                });
            });
        },
        connectSuccess: function (uid) {
            im.code.usuccess.uid = uid;
            im.connected = true;//连接成功

            listener(im.code.usuccess);

            if (msgQueue.length) {
                //队列中有消息，发送出去
                for (var i = 0; i < msgQueue.length; i++) {
                    im.sendMsg(msgQueue[i]);
                }
                msgQueue = [];
            }
            //加入到群组
            // userGroups.map(function (item) {
            //     im.joinGroup(item.id, item.groupname);
            // });

        },
        sendMsgWithQueue: function (data) {
            if (!im.connected) {
                log('当前服务器未连接，将消息加入到未发送队列');
                msgQueue.push(data);
            } else {
                this.sendMsg(data);
            }
        },
        sendMsg: function (data) {
            //根据layim提供的data数据，进行解析
            var mine = data.mine;
            var to = data.to;
            var id = conf.uid || mine.id;//当前用户id
            var group = to.type == 'group';
            if (group) {
                id = to.id;//如果是group类型，id就是当前groupid，切记不可写成 mine.id否则会出现一些问题。
            }
            //构造消息
            // var msg = {
            //     username: mine.username
            //           , avatar: mine.avatar
            //           , id: id
            //           , type: to.type
            //           , content: mine.content
            // }
            //这里要判断消息类型
            var conversationType = group ? RongIMLib.ConversationType.GROUP : RongIMLib.ConversationType.PRIVATE; //私聊,其他会话选择相应的消息类型即可。
            var targetId = to.id.toString();//这里的targetId必须是string类型，否则会报错
            //构造消息体，这里就是我们刚才已经注册过的自定义消息
            // log(msg);

            // var detail = new RongIMClient.RegisterMessage.LAYIM_TEXT_MESSAGE(msg);

            var msg = null;
            console.log(mine);
            mine.content = mine.content.replace(/face\[([^\s\[\]]+?)\]/g, function (face) {  //转义表情
                var alt = face.replace(/^face/g, '');
                return RongIMLib.RongIMEmoji.symbolToEmoji(alt);
            })
            if (mine.content.match(/img\([\s\S]+?\)\[([\s\S]+?)\]/g)) {
                var url = (mine.content.match(/img\(([\s\S]+?)\)\[/) || [])[1];
                var content = (mine.content.match(/\)\[([\s\S]*?)\]/) || [])[1];
                if (content.indexOf("data:image/png;base64,") >= 0) {
                    content = content.substring(22, content.length);
                }
                msg = new RongIMLib.ImageMessage({
                    content: content,
                    imageUri: fileData.src,
                    extra: "web",
                });
            } else if (mine.content.indexOf("file(") >= 0) {
                msg = new RongIMLib.FileMessage({
                    content: mine.content,
                    name: fileData.name,
                    size: fileData.size,
                    type: fileData.type,
                    fileUrl: fileData.src,
                    extra: to.type
                });
            } else if (mine.content.indexOf("audio[") >= 0) {
                msg = new RongIMClient.RegisterMessage.FATE_AUDIO_MESSAGE({
                    name: fileData.name,
                    size: fileData.size,
                    type: fileData.type,
                    fileUrl: fileData.src,
                    extra: to.type
                });
            } else if (mine.content.indexOf("video[") >= 0) {
                msg = new RongIMClient.RegisterMessage.FATE_VIDEO_MESSAGE({
                    name: fileData.name,
                    size: fileData.size,
                    type: fileData.type,
                    fileUrl: fileData.src,
                    extra: to.type
                });
            } else {
                msg = new RongIMLib.TextMessage({
                    content: mine.content,
                    extra: to.type
                });
            }
            msg.user = new RongIMLib.UserInfo(mine.id, mine.username, mine.avatar);
            msg.user.portrait = mine.avatar;

            //发送消息
            RongIMClient.getInstance().sendMessage(conversationType, targetId, msg, {
                onSuccess: function (message) {
                    log('发送消息成功');
                    log(message);
                    $.ajax({
                        url: "user/" + message.content.user.id
                        , type: 'get'
                        , data: {}
                        , dataType: 'json'
                        , success: function (res) {
                            if(conversationType==1){
                                dealMessage(message, res.user.nickname, res.user.avatar, message.targetId, "", res);
                            }else{
                                dealMessage(message, message.content.user.name, message.content.user.portraitUri || message.content.user.icon, message.targetId, "group");
                            }

                        }, error: function (err, msg) {
                            window.console && console.log && console.error('LAYIM_DATE_ERROR：' + msg);
                        }
                    });
                    sendReadReceipt(message);
                },
                onError: function (errorCode) {
                    log(errorCode);
                }
            });
        },
        startVoIP: function (data) {

            var WebIM = RongIMClient.getInstance();

            // var mediaType = RongIMLib.VoIPMediaType.MEDIA_VEDIO; //视频通话
            var mediaType = RongIMLib.VoIPMediaType.MEDIA_AUDIO; //音频通话
            var targetId = data.targetId.toString();//这里的targetId必须是string类型，否则会报错

            var converType = RongIMLib.ConversationType.PRIVATE;
            var invertUserIds = [];
            var extra = "";
            console.log(converType);
            WebIM.startCall(converType, targetId, invertUserIds, mediaType, extra, {
                onSuccess: function () {
                    // => startCall successfully
                    alert("success");
                },
                onError: function (err) {
                    // => startCall error
                    log(err);
                }
            });
        },
        joinGroup: function (gid, gname) {
            var groupId = (gid || '0').toString(); // 群 Id 。
            var groupName = gname;// 群名称 。
            RongIMClient.getInstance().joinGroup(groupId, groupName, {
                onSuccess: function () {
                    log('加入群成功');
                },
                onError: function (error) {
                    // error => 加入群失败错误码。
                }
            });
        },
        //初始化监听
        initListener: function () {
            // 设置连接监听状态 （ status 标识当前连接状态 ）
            // 连接状态监听器
            log('注册服务连接监听事件');
            var code = im.code.errorUnKnown;
            RongIMClient.setConnectionStatusListener({
                onChanged: function (status) {
                    switch (status) {
                        case RongIMLib.ConnectionStatus.CONNECTED:
                            code = null;
                            break;
                        case RongIMLib.ConnectionStatus.CONNECTING:
                            code = im.code.connecting;
                            break;
                        case RongIMLib.ConnectionStatus.DISCONNECTED:
                            code = im.code.disconnect;
                            break;
                        case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
                            code = im.code.errorOtherLogin;
                            break;
                        case RongIMLib.ConnectionStatus.DOMAIN_INCORRECT:
                            code = im.code.errorDomain;
                            break;
                        case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
                            code = im.code.errorNetwork;
                            break;
                    }
                    listener(code);
                }
            });

            // 消息监听器
            RongIMClient.setOnReceiveMessageListener({
                // 接收到的消息
                onReceived: function (message) {
                    console.log(message);
                    console.log(message.messageType);
                    var targetId;
                    var username;
                    var avatar;
                    if (message.conversationType == 1) {
                        if (message.content.user) {
                            $.ajax({
                                url: "user/" + message.targetId
                                , type: 'get'
                                , data: {}
                                , dataType: 'json'
                                , success: function (res) {
                                    targetId = conf.uid == message.senderUserId ? message.targetId : message.senderUserId;
                                    username = conf.uid != message.senderUserId ? res.user.nickname : message.content.user.name;
                                    avatar = conf.uid != message.senderUserId ? res.user.avatar : (message.content.user.portraitUri || message.content.user.icon);
                                    dealMessage(message, username, avatar, targetId, "", res);
                                }, error: function (err, msg) {
                                    window.console && console.log && console.error('LAYIM_DATE_ERROR：' + msg);
                                }
                            });
                        } else if (message.messageType != "ReadReceiptMessage") {
                            $.ajax({
                                url: "user/" + message.targetId
                                , type: 'get'
                                , data: {}
                                , dataType: 'json'
                                , success: function (res) {
                                    dealMessage(message, res.user.nickname, res.user.avatar, message.targetId, "", res);
                                }, error: function (err, msg) {
                                    window.console && console.log && console.error('LAYIM_DATE_ERROR：' + msg);
                                }
                            });

                        }


                    } else if (message.conversationType == 3) {
                        if (message.content.user) {
                            // $.ajax({
                            //     url: "group/"+message.targetId
                            //     , type: 'get'
                            //     , data: {}
                            //     , dataType: 'json'
                            //     , success: function (res) {
                            // targetId = conf.uid==message.senderUserId?message.targetId:message.senderUserId;
                            // username = conf.uid!=message.senderUserId?res.user.nickname:message.content.user.name;
                            // avatar = conf.uid!=message.senderUserId?res.user.avatar:(message.content.user.portraitUri || message.content.user.icon);
                            dealMessage(message, message.content.user.name, message.content.user.portraitUri || message.content.user.icon, message.targetId, "group");
                            // }, error: function (err, msg) {
                            //     window.console && console.log && console.error('LAYIM_DATE_ERROR：' + msg);
                            // }
                            // });
                        } else {
                            dealMessage(message, "", "", message.targetId, "group");
                        }
                    } else {
                        dealMessage(message, "", "", message.targetId, "sys");
                    }


                }
            });
        },
        //自定义消息
        defineMessage: function () {
            var defineMsg = function (obj) {
                RongIMClient.registerMessageType(obj.msgName, obj.objName, obj.msgTag, obj.msgProperties);
            }
            //注册普通消息
            var audioMsg = {
                msgName: 'MC_AUDIO_MESSAGE',
                objName: 'MC:AudioMsg',
                msgTag: new RongIMLib.MessageTag(false, false),
                msgProperties: ["name", "fileUrl", "user"]
            };
            //注册
            log('注册用户自定义消息类型：MC_AUDIO_MESSAGE');
            defineMsg(audioMsg);

            var videoMsg = {
                msgName: 'MC_VIDEO_MESSAGE',
                objName: 'MC:VideoMsg',
                msgTag: new RongIMLib.MessageTag(false, false),
                msgProperties: ["name", "fileUrl", "preview", "user"]
            };
            //注册
            log('注册用户自定义消息类型：MC_VIDEO_MESSAGE');
            defineMsg(videoMsg);

            var businessCardMsg = {
                msgName: 'CARD_MESSAGE',
                objName: 'RC:CardMsg',
                msgTag: new RongIMLib.MessageTag(false, false),
                msgProperties: ["userId", "name", "portraitUri", "user"]
            };
            //注册
            log('注册用户自定义消息类型：CARD_MESSAGE');
            defineMsg(businessCardMsg);

            var companyNtfMessage = {
                msgName: 'COMPANY_NTF_MESSAGE',
                objName: 'MC:CompanyNtf',
                msgTag: new RongIMLib.MessageTag(false, false),
                msgProperties: ["operation", "extra", "userId", "companyId", "message"]
            };
            //注册
            log('注册用户自定义消息类型：COMPANY_NTF_MESSAGE');
            defineMsg(companyNtfMessage);

            var momentNtfMessage = {
                msgName: 'MOMENT_NTF_MESSAGE',
                objName: 'MC:MomentNtf',
                msgTag: new RongIMLib.MessageTag(false, false),
                msgProperties: ["operation", "extra", "userId", "avatar", "count"]
            };
            //注册
            log('注册用户自定义消息类型：COMPANY_NTF_MESSAGE');
            defineMsg(momentNtfMessage);
        }
    };
    //token
    var token = {
        save: function (t) {
            layui.data('SOCKET_TOKEN', {
                key: conf.uid,
                value: t
            });
        },
        get: function (uid) {
            return layui.data('SOCKET_TOKEN')[uid] || '';
        },
        reset: function () {
            log("重置token");
            this.save('reset');
        }
    };


    var dealMessage = function (message, username, avatar, targetId, type, res) {
        var layim = conf.layim;
        // 判断消息类型
        switch (message.messageType) {
            case RongIMClient.MessageType.LAYIM_TEXT_MESSAGE:
                //message.content.timestamp = message.sentTime;
                conf.layim.getLayIMMessage(message.content);
                break;
            case RongIMClient.MessageType.InviteMessage:
                // 收到音视频通话邀请
                var isVideo = message.content.mediaType == mediaVideo;
                var node = isVideo ? acceptVideo : acceptAudio;
                show([node, hungup]);
                hide([video, audio]);
                break;
            case RongIMClient.MessageType.SummaryMessage:
                // 结束语音通话后收到
                hide([hungup, convert, acceptAudio, acceptVideo, mute, switcher]);
                show([video, audio]);
                break;
            case RongIMClient.MessageType.RingingMessage:
                // 响铃消息
                break;
            case RongIMClient.MessageType.AcceptMessage:
                // 同意接听音视频通话消息
                show([convert, mute, hungup]);
                hide([audio, video]);
                break;
            case RongIMClient.MessageType.MediaModifyMessage:
                // 视频转音频消息
                break;
            case RongIMClient.MessageType.TextMessage:
                //转换成layim认识的格式
                //要处理消息同步的情况
                var content = {
                    username: username
                    , avatar: avatar
                    , id: targetId
                    , fromid: message.senderUserId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , type: type
                    , content: message.content.content
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }

                break;
            case RongIMClient.MessageType.VoiceMessage:
                //转换成layim认识的格式
                var content = {
                    username: username
                    , avatar: avatar
                    , id: targetId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , fromid: message.senderUserId
                    , type: type
                    , content: "voice[" + message.content.content + "]"
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case RongIMClient.MessageType.ImageMessage:
                //转换成layim认识的格式
                var content = {
                    username: username
                    ,
                    avatar: avatar
                    ,
                    id: targetId
                    ,
                    targetName: res ? res.user.nickname : ""
                    ,
                    targetAvatar: res ? res.user.avatar : ""
                    ,
                    fromid: message.senderUserId
                    ,
                    type: type
                    ,
                    content: "img(" + message.content.imageUri + ")[" + (message.content.imageUri ? message.content.imageUri : message.content.content) + "]"
                    ,
                    imgContent: "img(" + message.content.imageUri + ")[" + message.content.content + "]"
                    ,
                    file: {
                        src: message.content.imageUri
                    }
                    ,
                    cid: message.messageUId
                    ,
                    message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case RongIMClient.MessageType.FileMessage:
                //转换成layim认识的格式
                var content = {
                    username: username
                    , avatar: avatar
                    , id: targetId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , fromid: message.senderUserId
                    , type: type
                    , content: "file(" + message.content.fileUrl + ")[" + message.content.name + "]"
                    , file: {
                        name: message.content.name,
                        size: message.content.size,
                        type: message.content.type,
                        src: message.content.fileUrl
                    }
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case RongIMClient.MessageType.LocationMessage:
                //转换成layim认识的格式
                var content = {
                    username: username
                    ,
                    avatar: avatar
                    ,
                    id: targetId
                    ,
                    targetName: res ? res.user.nickname : ""
                    ,
                    targetAvatar: res ? res.user.avatar : ""
                    ,
                    fromid: message.senderUserId
                    ,
                    type: type
                    ,
                    latitude: message.content.latitude
                    ,
                    longitude: message.content.longitude
                    ,
                    poi: message.content.poi
                    ,
                    content: "location[" + message.content.content + "," + message.content.latitude + "," + message.content.longitude + "," + message.content.poi + "]"
                    ,
                    cid: message.messageUId
                    ,
                    message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case RongIMClient.MessageType.InformationNotificationMessage:
                //转换成layim认识的格式
                var content = {
                    username: username
                    , avatar: avatar
                    , fromid: message.senderUserId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , system: true //系统消息
                    , id: targetId //聊天窗口ID
                    , type: type //聊天窗口类型
                    , content: message.content.message
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                break;
            case "GroupNotificationMessage":
                switch (message.content.operation) {
                    case "Create":
                    case "Rename":
                    case "Quit":
                        if (message.content.operation == "Rename") {
                            layim.setGroupName(message.targetId, message.content.data.targetGroupName);
                        }
                        //刷新群组,关闭主面板层，再create一个，哈哈哈机智→_→
                        if ($("#layui-layim").length == 0) {//有一种情况，在打开聊天的同时，一条建群通知传过来了，造成主面板不能正常刷新
                            break;
                        }
                        layer.close(mainIndex);
                        setTimeout(function () {
                            var init = {
                                url: 'lay/users' //接口地址（返回的数据格式见下文）
                                , type: 'get' //默认get，一般可不填
                                , data: {
                                    uid: conf.uid
                                } //额外参数
                            };
                            post(init, function (data) {
                                create(data);
                                setTimeout(function () {
                                    //发送系统消息
                                    var content = {
                                        system: true //系统消息
                                        , id: targetId //聊天窗口ID
                                        , type: type //聊天窗口类型
                                        , content: message.content.message
                                    };
                                    conf.layim.getMessage(content);
                                }, 1000);
                            }, 'INIT');
                        }, 300);
                        break;
                    default:
                        //发送系统消息
                        var content = {
                            system: true //系统消息
                            , id: targetId //聊天窗口ID
                            , type: type //聊天窗口类型
                            , content: message.content.message
                        };
                        conf.layim.getMessage(content);
                        break;
                }


                break;
            case "MC_AUDIO_MESSAGE":
                //转换成layim认识的格式
                var content = {
                    username: username
                    , avatar: avatar
                    , id: targetId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , fromid: message.senderUserId
                    , type: type
                    , content: "audio[" + message.content.fileUrl + "]"
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case "MC_VIDEO_MESSAGE":
                //转换成layim认识的格式
                var content = {
                    username: username
                    , avatar: avatar
                    , id: targetId
                    , targetName: res ? res.user.nickname : ""
                    , targetAvatar: res ? res.user.avatar : ""
                    , fromid: message.senderUserId
                    , type: type
                    , content: "video[" + message.content.fileUrl + "]"
                    , cid: message.messageUId
                    , message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;

            case "CARD_MESSAGE":
                //转换成layim认识的格式
                var content = {
                    username: username
                    ,
                    avatar: avatar
                    ,
                    id: targetId
                    ,
                    targetName: res ? res.user.nickname : ""
                    ,
                    targetAvatar: res ? res.user.avatar : ""
                    ,
                    fromid: message.senderUserId
                    ,
                    type: type
                    ,
                    content: "businessCard[" + message.content.userId + "," + message.content.name + "," + message.content.portraitUri + "]"
                    ,
                    cid: message.messageUId
                    ,
                    message: message
                };
                conf.layim.getMessage(content);
                if (conf.uid != message.senderUserId) {
                    //发送消息回执
                    sendReadReceipt(message);
                }
                break;
            case RongIMClient.MessageType.ReadReceiptMessage:
                break;
            case RongIMClient.MessageType.ContactNotificationMessage:
                if (message.content.operation == "responseFriend") {
                    layer.close(mainIndex);
                    setTimeout(function () {
                        var init = {
                            url: 'lay/users' //接口地址（返回的数据格式见下文）
                            , type: 'get' //默认get，一般可不填
                            , data: {
                                uid: conf.uid
                            } //额外参数
                        };
                        post(init, function (data) {
                            create(data);
                            setTimeout(function () {
                                //发送系统消息
                                var content = {
                                    system: true //系统消息
                                    , id: targetId //聊天窗口ID
                                    , type: type //聊天窗口类型
                                    , content: message.content.message
                                };
                                conf.layim.getMessage(content);
                            }, 1000);
                        }, 'INIT');
                    }, 300);
                }

                //更新消息盒子的消息数量
                getMsgNum(conf.layim);
                break;
            case "MOMENT_NTF_MESSAGE":
                //转换成layim认识的格式
                getMomentStatus(conf.uid);
                break;
            case "RecallCommandMessage":
                //转换成layim认识的格式
                var msgId = message.content.messageUId;
                rollback(msgId, targetId, username, message.conversationType==1?"":"group");
                break;
        }
    };

    var rollback = function (msgId, targetId, username, type) {
        $("li[data-cid=" + msgId + "]").remove();
        var content = {
            system: true //系统消息
            , id: targetId //聊天窗口ID
            , type: type //聊天窗口类型
            , content: username + "撤回了一条消息"
        };
        conf.layim.getMessage(content);

        var cache = layui.layim.cache();
        var local = layui.data('layim')[cache.mine.id]; //获取当前用户本地数据

        //这里以删除本地聊天记录为例

        var chatlog = local.chatlog[targetId];
        for (var i in chatlog) {
            if (chatlog[i].cid == msgId) {
                chatlog.splice(i, 1);
                break;
            }
        }
        //向localStorage同步数据
        layui.data('layim', {
            key: cache.mine.id
            , value: local
        });
    }

    var sendReadReceipt = function (message) {
        var read = RongIMLib.ReadReceiptMessage.obtain(message.messageUId, message.sentTime, "1");

        RongIMClient.getInstance().sendMessage(message.conversationType, message.targetId, read, {
            onSuccess: function (message) {
                log(message);
            },
            onError: function (errorCode) {
                log(errorCode);
            }
        });
    };

    var video = $('#video'),
        audio = $('#audio'),
        mute = $('#mute'),
        convert = $('#convert'),
        acceptVideo = $('#acceptVideo'),
        acceptAudio = $('#acceptAudio'),
        hungup = $('#hungup');
    switcher = $('#switchVideoWindows');
    var show = function(nodes){
        $(nodes).each(function (index, item){
            item.show();
        });
    };
    var hide = function(nodes){
        $(nodes).each(function (index, item){
            item.hide();
        });
    };

    var mediaVideo = RongIMLib.VoIPMediaType.MEDIA_VEDIO; //视频通话
    var mediaAudio = RongIMLib.VoIPMediaType.MEDIA_AUDIO; //音频通话
    var ErrorCode = RongIMLib.ErrorCode;

    var startConversation = function(conversationType, targetId, inviteUserId, mediaType, extra){
        CallLib.startCall(conversationType, targetId, inviteUserId, mediaType, extra, {
            onSuccess:function(){
                show([hungup]);
                hide([video, audio]);
            },
            onError:function(err){
                console.log('startCall error', err);
            }
        });
    };

    // 发起视频通话
    video.click(function(){
        startConversation(conversationType, targetId, [inviteUserId], mediaVideo, extra);
    });
    // 发起音频通话
    audio.click(function(){
        startConversation(conversationType, targetId, [inviteUserId], mediaAudio, extra);
    });
    var switchVideoWindows = function(){
        var myScreen, otherScreen, canvas;
        if($("#main > div:first").attr('id') === undefined){
            myScreen = $("#main > div:first");
            otherScreen = $("#main > div:eq(1)");
        } else {
            myScreen = $("#main > div:eq(1)");
            otherScreen = $("#main > div:first");
        }
        myScreen.toggleClass('mainSmall');
        otherScreen.toggleClass('big');
    };

    // 音视频互转 TODO 功能未调通
    var conversationItem = {
        method: 'videoToAudio',
        videoToAudio: function(btn){
            btn.val('音频转视频');
            this.method = 'audioToVideo';
        },
        audioToVideo: function(btn){
            btn.val('视频转音频');
            this.method = 'videoToAudio';
        }
    };
    convert.click(function(){
        var method = conversationItem.method;
        CallLib[method](conversationType, targetId, function(){
            conversationItem[method](convert);
        });
    });
    switcher.click(function(){
        switchVideoWindows();
    });

    // 静音、取消静音
    var muteItem = {
        method:'mute',
        mute: function(btn){
            btn.val('取消静音');
            this.method = 'unmute';
        },
        unmute: function(btn){
            btn.val('静音');
            this.method = 'mute';
        }
    };
    mute.click(function(){
        var method = muteItem.method;
        CallLib[method](function(){
            muteItem[method](mute);
        });
    });
    // 接听视频
    acceptVideo.click(function(){
        CallLib.joinCall(mediaVideo,{
            onSuccess:function(){
                show([mute, convert, switcher]);
                hide([video, audio, acceptVideo]);
            },
            onError:function(err){
                console.log("acceptVideo err ",err);
            }
        });
    });
    // 仅接听音频
    acceptAudio.click(function(){
        CallLib.joinCall(mediaAudio,{
            onSuccess:function(){
                show([mute, convert, switcher]);
                hide([video, audio, acceptAudio]);
            },
            onError:function(err){
                console.log("acceptVideo err ",err);
            }
        });
    });
    // 挂断
    hungup.click(function(){
        CallLib.hungupCall(conversationType,targetId, ErrorCode.HANGUP);
    });

    //Ajax
    var post = function (options, callback, tips) {
        options = options || {};
        return $.ajax({
            url: options.url
            , type: options.type || 'get'
            , data: options.data
            , dataType: options.dataType || 'json'
            , cache: false
            , success: function (res) {
                res.code == 0 || res.result.success
                    ? callback && callback(res.data || {})
                    : layer.msg(res.msg || ((tips || 'Error') + ': LAYIM_NOT_GET_DATA'), {
                        time: 5000
                    });
            }, error: function (err, msg) {
                window.console && console.log && console.error('LAYIM_DATE_ERROR：' + msg);
            }
        });
    };

    exports('socket', socket)
});