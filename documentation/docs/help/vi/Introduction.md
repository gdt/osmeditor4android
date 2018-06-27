# Giới thiệu về Vespucci

Vespucci là một chương trình sửa đổi OpenStreetMap có chức năng đầy đủ, kể cả các tác vụ đuợc hỗ trợ bởi các chương trình sửa đổi dành cho máy tính để bàn. Nó đã được kiểm thử thành công trong Android của Google từ 2.3 cho tới 7.0 cũng như một số biến thể gốc AOSP. Xin cẩn thận: tuy các thiết bị di động có khả năng gần như bằng với các máy tính để bàn, các thiết bị di động, nhất là các thiết bị cũ hơn, có bộ nhớ hạn chế và chạy tương đối chậm chạp. Bạn nên nghĩ đến điều này trong việc sử dụng Vespucci và chẳng hạn cố gắng sửa đổi những khu vực có kích thước hợp lý. 

## Lần sử dụng đầu tiên

Khi khởi động, Vespucci hiển thị hộp thoại “Tải về vị trí khác”/“Tải Khu vực”. Nếu bạn biết tọa độ và muốn tải về ngay, chọn mục tọa độ và đặt bán kính bao quanh vị trí muốn tải về. Đừng chọn một khu vực lớn trên một thiết bị chậm chạp. 

Thay thế, bấm nút “Đi đến bản đồ” để bỏ qua hộp thoại, di chuyển đến vị trí mà bạn muốn sửa đổi, và tải về dữ liệu (xem “Sửa đổi bằng Vespucci” bên dưới).

## Sửa đổi bằng Vespucci

Tùy theo kích thước màn hình và thế hệ của thiết bị, các tác vụ sửa đổi có thể được truy cập trực tiếp qua các hình tượng trên thanh trên, qua một trình đơn thả xuống vào bên phải của thanh trên, từ thanh dưới (nếu có), hoặc bằng cách bấm phím trình đơn.

<a id="download"></a>

### Tải về dữ liệu OSM

Chọn hình tượng truyền ![Truyền](../images/menu_transfer.png) hoặc mục “Truyền” trong trình đơn. Bảy mục sẽ xuất hiện:

* **Download current view** - download the area visible on the screen and replace any existing data *(requires network connectivity)*
* **Add current view to download** - download the area visible on the screen and merge it with existing data *(requires network connectivity)*
* **Download at other location** - shows a form that allows you to enter coordinates, search for a location or use the current position, and then download an area around that location *(requires network connectivity)*
* **Upload data to OSM server** - upload edits to OpenStreetMap *(requires authentication)* *(requires network connectivity)*
* **Auto download** - download an area around the current geographic location automatically *(requires network connectivity)* *(requires GPS)*
* **File...** - saving and loading OSM data to/from on device files.
* **Note/Bugs...** - download (automatically and manually) OSM Notes and "Bugs" from QA tools (currently OSMOSE) *(requires network connectivity)*

The easiest way to download data to the device is to zoom and pan to the location you want to edit and then to select "Download current view". You can zoom by using gestures, the zoom buttons or the volume control buttons on the device.  Vespucci should then download data for the current view. No authentication is required for downloading data to your device.

### Sửa đổi

<a id="lock"></a>

#### Khóa, mở khóa, thay đổi chế độ

Để tránh sửa đổi nhầm lẫn, Vespucci bắt đầu trong chế độ “khóa” chỉ cho phép thu phóng và cuộn bản đồ. Chạm hình ![Locked](../images/locked.png) để mở khóa màn hình. 

Chạm lâu vào hình ổ khóa để hiển thị trình đơn có bốn tùy chọn:

* **Normal** - the default editing mode, new objects can be added, existing ones edited, moved and removed. Simple white lock icon displayed.
* **Tag only** - selecting an existing object will start the Property Editor, a long press on the main screen will add objects, but no other geometry operations will work. White lock icon with a "T" is displayed.
* **Indoor** - enables Indoor mode, see [Indoor mode](#indoor). White lock icon with a "I" is displayed.
* **C-Mode** - enables C-Mode, only objects that have a warning flag set will be displayed, see [C-Mode](#c-mode). White lock icon with a "C" is displayed.

#### Chạm, chạm đúp, và bấm lâu

Theo mặc định, các nốt và lối chọn được tô màu cam để cho biết vùng phải chạm để chọn đối tượng đó. Có ba kiểu tương tác với đối tượng:

* Single tap: Selects object. 
    * An isolated node/way is highlighted immediately. 
    * However, if you try to select an object and Vespucci determines that the selection could mean multiple objects it will present a selection menu, enabling you to choose the object you wish to select. 
    * Selected objects are highlighted in yellow. 
    * For further information see [Node selected](../en/Node%20selected.md), [Way selected](../en/Way%20selected.md) and [Relation selected](../en/Relation%20selected.md).
* Double tap: Start [Multiselect mode](../en/Multiselect.md)
* Long press: Creates a "crosshair", enabling you to add nodes, see below and [Creating new objects](../en/Creating%20new%20objects.md)

Nếu muốn sửa đổi một khu vực có nhiều dữ liệu, bạn rất nên phóng to trước khi sửa đổi.

Vespucci có đầy đủ chức năng hoàn tác/làm lại – đừng có sợ thử nghiệm trên thiết bị của bạn. Tuy nhiên, xin vui lòng đừng tải lên và lưu dữ liệu thử nghiệm.

#### Chọn / bỏ chọn (chạm và “trình đơn lựa chọn”)

Touch an object to select and highlight it. Touching the screen in an empty region will de-select. If you have selected an object and you need to select something else, simply touch the object in question, there is no need to de-select first. A double tap on an object will start [Multiselect mode](../en/Multiselect.md).

Note that if you try to select an object and Vespucci determines that the selection could mean multiple objects (such as a node on a way or other overlapping objects) it will present a selection menu: Tap the object you wish to select and the object is selected. 

Selected objects are indicated through a thin yellow border. The yellow border may be hard to spot, depending on map background and zoom factor. Once a selection has been made, you will see a notification confirming the selection.

Once the selection has completed you will see (either as buttons or as menu items) a list of supported operations for the selected object: For further information see [Node selected](../en/Node%20selected.md), [Way selected](../en/Way%20selected.md) and [Relation selected](../en/Relation%20selected.md).

#### Đối tượng đã chọn: Sửa đổi thẻ

A second touch on the selected object opens the tag editor and you can edit the tags associated with the object.

Note that for overlapping objects (such as a node on a way) the selection menu comes back up for a second time. Selecting the same object brings up the tag editor; selecting another object simply selects the other object.

#### Đối tượng đã chọn: Di chuyển nốt hoặc lối

Once you have selected an object, it can be moved. Note that objects can be dragged/moved only when they are selected. Simply drag near (i.e. within the tolerance zone of) the selected object to move it. If you select the large drag area in the preferences, you get a large area around the selected node that makes it easier to position the object. 

#### Thêm nốt/địa điểm hoặc lối mới (bấm lâu)

Long press where you want the node to be or the way to start. You will see a black "crosshair" symbol. 
* If you want to create a new node (not connected to an object), click away from existing objects.
* If you want to extend a way, click within the "tolerance zone" of the way (or a node on the way). The tolerance zone is indicated by the areas around a node or way.

Khi bạn mới thấy hình tâm ngắm, bạn có thể:

* Touch in the same place.
    * If the crosshair is not near a node, touching the same location again creates a new node. If you are near a way (but not near a node), the new node will be on the way (and connected to the way).
    * If the crosshair is near a node (i.e. within the tolerance zone of the node), touching the same location just selects the node (and the tag editor opens. No new node is created. The action is the same as the selection above.
* Touch another place. Touching another location (outside of the tolerance zone of the crosshair) adds a way segment from the original position to the current position. If the crosshair was near a way or node, the new segment will be connected to that node or way.

Simply touch the screen where you want to add further nodes of the way. To finish, touch the final node twice. If the final node is  located on a way or node, the segment will be connected to the way or node automatically. 

Bạn cũng có thể sử dụng mục trình đơn: xem thêm thông tin tại [Creating new objects](../vi/Tạo%20đối%20tượng%20mới.md).

#### Thêm Vùng

OpenStreetMap currently doesn't have an "area" object type unlike other geo-data systems. The online editor "iD" tries to create an area abstraction from the underlying OSM elements which works well in some circumstances, in others not so. Vespucci currently doesn't try to do anything similar, so you need to know a bit about the way areas are represented:

* _closed ways (*polygons")_: the simplest and most common area variant, are ways that have a shared first and last node forming a closed "ring" (for example most buildings are of this type). These are very easy to create in Vespucci, simply connect back to the first node when you are finished with drawing the area. Note: the interpretation of the closed way depends on its tagging: for example if a closed way is tagged as a building it will be considered an area, if it is tagged as a roundabout it wont. In some situations in which both interpretations may be valid, an "area" tag can clarify the intended use.
* _multi-ploygons_: some areas have multiple parts, holes and rings that can't be represented with just one way. OSM uses a specific type of relation (our general purpose object that can model relations between elements) to get around this, a multi-polygon. A multi-polygon can have multiple "outer" rings, and multiple "inner" rings. Each ring can either be a closed way as described above, or multiple individual ways that have common end nodes. While large multi-polygons are difficult to handle with any tool, small ones are not difficult to create in Vespucci. 
* _coastlines_: for very large objects, continents and islands, even the multi-polygon model doesn't work in a satisfactory way. For natural=coastline ways we assume direction dependent semantics: the land is on the left side of the way, the water on the right side. A side effect of this is that, in general, you shouldn't reverse the direction of a way with coastline tagging. More information can be found on the [OSM wiki](http://wiki.openstreetmap.org/wiki/Tag:natural%3Dcoastline).

#### Cải thiện hình dạng lối

If you zoom in far enough on a selected way you will see a small "x" in the middle of the way segments that are long enough. Dragging the "x" will create a node in the way at that location. Note: to avoid accidentally creating nodes, the touch tolerance area for this operation is fairly small.

#### Cắt, sao chép, dán

You can copy or cut selected nodes and ways, and then paste once or multiple times to a new location. Cutting will retain the osm id and version. To paste long press the location you want to paste to (you will see a cross hair marking the location). Then select "Paste" from the menu.

#### Ghi địa chỉ một cách tiện lợi

Vespucci có chức năng “thêm thẻ địa chỉ” để làm tiện việc lấy các địa chỉ. Để sử dụng chức năng này:

* nhấn giữ: Vespucci sẽ đặt một nốt vào vị trí, cố gắng đoán ra số nhà, và thêm các thẻ địa chỉ mà bạn đã sử dụng nhiều gần đây. Nếu nốt nằm trên đường nét tòa nhà, thẻ “entrance=yes” sẽ được tự động thêm vào nốt. Trình sửa đổi thẻ sẽ mở lên để cho bạn chỉnh lại đối tượng nếu cần.
* chọn nốt hoặc lối: Vespucci sẽ thêm thẻ địa chỉ như bên trên và mở trình sửa đổi thẻ.
* mở trình sửa đổi thẻ.

Để đoán ra số nhà, thường phải có mỗi bên đường là ít nhất hai số nhà, càng thêm số nhà càng chính xác hơn.

Hãy thử sử dụng chức năng này trong chế độ [Tự động tải về](#download).  

#### Thêm hạn chế rẽ

Vespucci cho phép ghi hạn chế rẽ một cách nhanh nhẹn. Nếu cần, nó sẽ tự động cắt đôi các lối, và nếu cần, nó sẽ xin bạn chọn lại các đối tượng. 

* select a way with a highway tag (turn restrictions can only be added to highways, if you need to do this for other ways, please use the generic "create relation" mode)
* select "Add restriction" from the menu
* select the "via" node or way (only possible "via" elements will have the touch area shown)
* select the "to" way (it is possible to double back and set the "to" element to the "from" element, Vespucci will assume that you are adding an no_u_turn restriction)
* set the restriction type

### Vespucci trong chế độ “khóa”

When the red lock is displayed all non-editing actions are available. Additionally a long press on or near to an object will display the detail information screen if it is an OSM object.

### Lưu các thay đổi của bạn

*(cần kết nối mạng)*

Chọn cùng nhút hoặc mục trình đơn mà bạn đã chọn để tải về và sau đó chọn “Tải dữ liệu lên máy chủ OSM”.

Vespucci supports OAuth authorization and the classical username and password method. OAuth is preferable since it avoids sending passwords in the clear.

New Vespucci installs will have OAuth enabled by default. On your first attempt to upload modified data, a page from the OSM website loads. After you have logged on (over an encrypted connection) you will be asked to authorize Vespucci to edit using your account. If you want to or need to authorize the OAuth access to your account before editing there is a corresponding item in the "Tools" menu.

Nếu muốn lưu các thay đổi của bạn đang khi ngoại tuyến, bạn có thể lưu vào một tập tin .osm tương thích với OSM để tải lên về sau hoặc với Vespucci hoặc với JOSM. 

#### Giải quyết mâu thuẫn khi tải lên

Vespucci has a simple conflict resolver. However if you suspect that there are major issues with your edits, export your changes to a .osc file ("Export" menu item in the "Transfer" menu) and fix and upload them with JOSM. See the detailed help on [conflict resolution](../en/Conflict%20resolution.md).  

## Sử dụng GPS

You can use Vespucci to create a GPX track and display it on your device. Further you can display the current GPS position (set "Show location" in the GPS menu) and/or have the screen center around and follow the position (set "Follow GPS Position" in the GPS menu). 

If you have the latter set, moving the screen manually or editing will cause the "follow GPS" mode to be disabled and the blue GPS arrow will change from an outline to a filled arrow. To quickly return to the "follow" mode, simply touch GPS button or re-check the menu option.

## Ghi chú và lỗi

Vespucci supports downloading, commenting and closing of OSM Notes (formerly OSM Bugs) and the equivalent functionality for "Bugs" produced by the [OSMOSE quality assurance tool](http://osmose.openstreetmap.fr/en/map/). Both have to either be down loaded explicitly or you can use the auto download facility to access the items in your immediate area. Once edited or closed, you can either upload the bug or Note immediately or upload all at once.

Trên bản đồ, các ghi chú và lỗi được hiển thị với hình bọ nhỏ ![Lỗi](../images/bug_open.png). Các bọ màu xanh lục chỉ ra ghi chú được đóng/giải quyết, màu xanh lam là do bạn tạo hoặc sửa đổi, và màu vàng có nghĩa rằng nó vẫn đang tích cực và chưa được thay đổi. 

The OSMOSE bug display will provide a link to the affected object in blue, touching the link will select the object, center the screen on it and down load the area beforehand if necessary. 

### Lọc

Besides globally enabling the notes and bugs display you can set a coarse grain display filter to reduce clutter. In the "Advanced preferences" you can individually select:

* Ghi chú
* Lỗi Osmose
* Cảnh báo Osmose
* Vấn đề nhỏ Osmose

<a id="indoor"></a>

## Chế độ trong nhà

Mapping indoors is challenging due to the high number of objects that very often will overlay each other. Vespucci has a dedicated indoor mode that allows you to filter out all objects that are not on the same level and which will automatically add the current level to new objects created their.

The mode can be enabled by long pressing on the lock item, see [Lock, unlock, mode switching](#lock) and selecting the corresponding menu entry.

<a id="c-mode"></a>

## Chế độ C

In C-Mode only objects are displayed that have a warning flag set, this makes it easy to spot objects that have specific problems or match configurable checks. If an object is selected and the Property Editor started in C-Mode the best matching preset will automatically be applied.

The mode can be enabled by long pressing on the lock item, see [Lock, unlock, mode switching](#lock) and selecting the corresponding menu entry.

### Cấu hình bộ kiểm tra

Currently there are two configurable checks (there is a check for FIXME tags and a test for missing type tags on relations that are currently not configurable) both can be configured by selecting "Validator preferences" in the "Preferences". 

The list of entries is split in to two, the top half lists "re-survey" entries, the bottom half check "entries". Entries can be edited by clicking them, the green menu button allows adding of entries.

#### Mục khảo sát lại

Mỗi mục khảo sát lại có các thuộc tính sau:

* **Key** - Key of the tag of interest.
* **Value** - Value the tag of interest should have, if empty the tag value will be ignored.
* **Age** - how many days after the element was last changed the element should be re-surveyed, if a check_date field is present that will be the used, otherwise the date the current version was create. Setting the value to zero will lead to the check simply matching against key and value.
* **Regular expression** - if checked **Value** is assumed to be a JAVA regular expression.

**Key** and **Value** are checked against the _existing_ tags of the object in question.

#### Mục kiểm tra

Mỗi mục kiểm tra có hai thuộc tính sau:

* **Key** - Key that should be present on the object according to the matching preset.
* **Check optional** - Check the optional tags of the matching preset.

This check works be first determining the matching preset and then checking if **Key** is a "recommended" key for this object according to the preset, **Check optional** will expand the check to tags that are "optional* on the object. Note: currently linked presets are not checked.

## Bộ lọc

### Bộ lọc theo thẻ

The filter can be enabled from the main menu, it can then be changed by tapping the filter icon. More documentation can be found here [Tag filter](../en/Tag%20filter.md).

### Bộ lọc theo thẻ đặt trước

An alternative to the above, objects are filtered either on individual presets or on preset groups. Tapping on the filter icon will display a preset selection dialog similar to that used elsewhere in Vespucci. Individual presets can be selected by a normal click, preset groups by a long click (normal click enters the group). More documentation can be found here [Preset filter](../en/Preset%20filter.md).

## Tùy chỉnh Vespucci

### Các thiết lập có thể muốn thay đổi

* Background layer
* Overlay layer. Adding an overlay may cause issues with older devices and such with limited memory. Default: none.
* Notes/Bugs display. Open Notes and bugs will be displayed as a yellow bug icon, closed ones the same in green. Default: on.
* Photo layer. Displays geo-referenced photographs as red camera icons, if direction information is available the icon will be rotated. Default: off.
* Node icons. Default: on.
* Keep screen on. Default: off.
* Large node drag area. Moving nodes on a device with touch input is problematic since your fingers will obscure the current position on the display. Turning this on will provide a large area which can be used for off-center dragging (selection and other operations still use the normal touch tolerance area). Default: off.

#### Tùy chỉnh nâng cao

* Always show context menu. When turned on every selection process will show the context menu, turned off the menu is displayed only when no unambiguous selection can be determined. Default: off (used to be on).
* Enable light theme. On modern devices this is turned on by default. While you can enable it for older Android versions the style is likely to be inconsistent.
* Show statistics. Will show some statistics for debugging, not really useful. Default: off (used to be on).  

## Báo cáo lỗi

If Vespucci crashes, or it detects an inconsistent state, you will be asked to send in the crash dump. Please do so if that happens, but please only once per specific situation. If you want to give further input or open an issue for a feature request or similar, please do so here: [Vespucci issue tracker](https://github.com/MarcusWolschon/osmeditor4android/issues). If you want to discuss something related to Vespucci, you can either start a discussion on the [Vespucci Google group](https://groups.google.com/forum/#!forum/osmeditor4android) or on the [OpenStreetMap Android forum](http://forum.openstreetmap.org/viewforum.php?id=56)

