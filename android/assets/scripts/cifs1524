--
-- Created by IntelliJ IDEA.
-- User: lake
-- Date: 14/12/25
-- Time: 15:12
--
-- Input basic management

function onTouchDown(x, y)
    local touched = nya:getThingAt(x, y)

    --Default: walk
    if touched == Nil then
        protagonist:walkTo(x, y, false)
    else
        touched:interact("use")
    end
end
