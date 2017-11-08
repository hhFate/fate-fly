var sizeConvert = function(size){
    if(size<1024){
        return size+"字节";
    }else if(size<1024*1024){
        return (size*1.0/1024).toFixed(2)+"K";
    }else if(size<1024*1024*1024){
        return (size*1.0/1024/1024).toFixed(2)+"M";
    }else{
        return (size*1.0/1024/1024/1024).toFixed(2)+"G";
    }
}