// global timer랑 interval은 재할당 가능
let gTimer = null
let gSetTimer = null
let prtSetTimer = null
let gInterval = null

// 저울 - 타이머 interval
export const globalInterval = (cb) => {
    if (gInterval) clearInterval(gInterval)
    gInterval = setInterval(cb, 1000)
}

// 저울 - 타임아웃 function
export const globalTimeout = (cb, time) => {
    if (gTimer) clearTimeout(gTimer)
    gTimer = setTimeout(() => {
        if (gInterval) clearInterval(gInterval)
        cb()
    }, time)
}

// 단순 setTimeout
export const gSetTimeout = (cb, time) => {
    gSetTimer = setTimeout(cb, time)
}

// 프린트 1초 후 해지 setTimeout
export const prtSetTimeout = (cb, time) => {
    prtSetTimer = setTimeout(cb, time)
}

// 프린트 1초 후 해지 clerTimeout
export const prtClearTimeout = (cb, time) => {
    if (prtSetTimer) {
        clearTimeout(prtSetTimer)
        prtSetTimer = null
    }
}

// 타이머, 타임아웃 reset
export const timeIntervalReset = () => {
    if (gInterval) {
        clearInterval(gInterval)
        gInterval = null
    }
    if (gTimer) {
        clearTimeout(gTimer)
        gTimer = null
    }
    if (gSetTimer) {
        clearTimeout(gSetTimer)
        gSetTimer = null
    }
}

// 쿼리 string object
export const getQueryStringObject = () => {
    var a = window.location.search.substr(1).split('&')
    if (a == '') return {}
    var b = {}
    for (var i = 0; i < a.length; ++i) {
        var p = a[i].split('=', 2)
        if (p.length == 1)
            b[p[0]] = ''
        else
            b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, ' '))
    }
    return b
}
