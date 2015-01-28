--
-- Created by IntelliJ IDEA.
-- User: lake
-- Date: 14/12/25
-- Time: 15:12
--
-- Introduction

function centeredText(text)
    local tw = nya:textWidth(text)
    local th = nya:textHeight(text)
    local x = nya:getScreenWidth()/2 - tw/2
    local y = nya:getScreenHeight()/2 + th/2
    nya:displayText(text, x, y)
end

function textNWait(text, time)
    centeredText(text)
    nya:wait(time)
end

function afterFade()
    nya:beginCutscene()
    textNWait("883495... 883496... 883497...", 2.0)
    textNWait("883498... 883499... 883500...", 2.0)
    textNWait(".............................", 2.0)
    textNWait("............................!", 2.0)
    textNWait("883501... 883502... 883503...", 2.0)
    nya:endCutscene()

    nya:changeRoom(1)
end
