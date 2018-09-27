{
	"patcher" : 	{
		"fileversion" : 1,
		"rect" : [ 676.0, 96.0, 181.0, 660.0 ],
		"bglocked" : 0,
		"defrect" : [ 676.0, 96.0, 181.0, 660.0 ],
		"openrect" : [ 0.0, 0.0, 0.0, 0.0 ],
		"openinpresentation" : 1,
		"default_fontsize" : 12.0,
		"default_fontface" : 0,
		"default_fontname" : "Arial",
		"gridonopen" : 1,
		"gridsize" : [ 10.0, 10.0 ],
		"gridsnaponopen" : 0,
		"toolbarvisible" : 0,
		"boxanimatetime" : 200,
		"imprint" : 0,
		"metadata" : [  ],
		"boxes" : [ 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "release",
					"presentation_rect" : [ 94.0, 98.0, 50.0, 20.0 ],
					"patching_rect" : [ 985.0, 60.0, 50.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-29",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "number",
					"presentation_rect" : [ 93.0, 120.0, 50.0, 20.0 ],
					"outlettype" : [ "int", "bang" ],
					"patching_rect" : [ 984.0, 82.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-20",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "release $&",
					"outlettype" : [ "" ],
					"patching_rect" : [ 984.0, 106.0, 68.0, 18.0 ],
					"id" : "obj-12",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "textbutton",
					"presentation_rect" : [ 43.0, 19.0, 46.0, 21.0 ],
					"outlettype" : [ "", "", "int" ],
					"patching_rect" : [ 45.0, 3.0, 46.0, 21.0 ],
					"presentation" : 1,
					"id" : "obj-16",
					"fontname" : "Arial",
					"rounded" : 5.0,
					"text" : "Open",
					"numinlets" : 1,
					"bordercolor" : [ 0.6, 0.6, 0.6, 0.0 ],
					"fontsize" : 12.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "t 1 b open",
					"outlettype" : [ "int", "bang", "open" ],
					"patching_rect" : [ 45.0, 31.0, 64.0, 20.0 ],
					"id" : "obj-3",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "mxj~ BeatDetect",
					"outlettype" : [ "signal", "" ],
					"patching_rect" : [ 72.0, 224.0, 99.0, 20.0 ],
					"id" : "obj-1",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "+ 1",
					"outlettype" : [ "int" ],
					"patching_rect" : [ 506.0, 257.0, 32.5, 20.0 ],
					"id" : "obj-145",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"presentation_rect" : [ 26.0, 337.0, 124.0, 20.0 ],
					"items" : [ 1, 10, ",", 10, 50, ",", 100, 200, ",", 0, 500, ",", 0, 1000 ],
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 712.0, 145.0, 100.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-132",
					"fontname" : "Arial",
					"arrowbgcolor" : [ 0.266667, 0.266667, 0.266667, 0.0 ],
					"types" : [  ],
					"framecolor" : [ 0.066667, 0.066667, 0.066667, 0.0 ],
					"arrowframe" : 0,
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "synced freeze length",
					"presentation_rect" : [ 25.0, 183.0, 121.0, 20.0 ],
					"patching_rect" : [ 470.0, 80.0, 121.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-127",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "loadbang",
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 111.0, 63.0, 60.0, 20.0 ],
					"id" : "obj-125",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "file looper",
					"presentation_rect" : [ 92.0, 19.0, 63.0, 20.0 ],
					"patching_rect" : [ 161.0, 86.0, 63.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-124",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "freeze",
					"presentation_rect" : [ 80.0, 395.0, 44.0, 20.0 ],
					"patching_rect" : [ 753.0, 389.0, 44.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-122",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "length (ms)",
					"presentation_rect" : [ 80.0, 491.5, 71.0, 20.0 ],
					"patching_rect" : [ 663.0, 438.0, 71.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-121",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "dB",
					"presentation_rect" : [ 134.0, 55.0, 25.0, 20.0 ],
					"patching_rect" : [ 131.0, 168.0, 25.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-119",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "dry/wet",
					"presentation_rect" : [ 111.0, 525.0, 49.0, 20.0 ],
					"patching_rect" : [ 955.0, 472.0, 49.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-117",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "length control",
					"presentation_rect" : [ 84.0, 160.0, 83.0, 20.0 ],
					"patching_rect" : [ 446.0, 409.0, 83.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-115",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "init plug",
					"presentation_rect" : [ 95.0, 551.0, 52.0, 20.0 ],
					"patching_rect" : [ 218.0, 534.0, 52.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-112",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "button",
					"presentation_rect" : [ 72.0, 550.0, 20.0, 20.0 ],
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 195.0, 533.0, 20.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-110",
					"numinlets" : 1,
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"outlinecolor" : [ 0.0, 0.0, 0.0, 0.156863 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "init min",
					"presentation_rect" : [ 41.0, 205.0, 49.0, 20.0 ],
					"patching_rect" : [ 513.0, 111.0, 49.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-108",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "button",
					"presentation_rect" : [ 19.0, 205.0, 20.0, 20.0 ],
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 489.0, 111.0, 20.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-105",
					"numinlets" : 1,
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"outlinecolor" : [ 0.0, 0.0, 0.0, 0.094118 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "max beat mult",
					"presentation_rect" : [ 77.0, 257.0, 86.0, 20.0 ],
					"patching_rect" : [ 519.0, 285.0, 86.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-103",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "max beat div",
					"presentation_rect" : [ 77.0, 231.0, 79.0, 20.0 ],
					"patching_rect" : [ 559.0, 229.0, 79.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-102",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "beat divider",
					"presentation_rect" : [ 77.0, 282.0, 73.0, 20.0 ],
					"patching_rect" : [ 481.0, 310.0, 73.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-101",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "random freeze length",
					"presentation_rect" : [ 27.0, 315.0, 124.0, 20.0 ],
					"patching_rect" : [ 729.0, 199.0, 124.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-98",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 99.0, 361.0, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 762.0, 172.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-95",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 27.0, 360.0, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 710.0, 172.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-94",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "attack",
					"presentation_rect" : [ 34.0, 132.0, 43.0, 20.0 ],
					"patching_rect" : [ 895.0, 126.0, 43.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-92",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "auto",
					"presentation_rect" : [ 80.0, 419.5, 34.0, 20.0 ],
					"patching_rect" : [ 749.0, 336.0, 34.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-87",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "duration (ms)",
					"presentation_rect" : [ 80.0, 467.5, 81.0, 20.0 ],
					"patching_rect" : [ 908.0, 335.0, 91.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-85",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "comment",
					"text" : "delay (ms)",
					"presentation_rect" : [ 80.0, 443.5, 67.0, 20.0 ],
					"patching_rect" : [ 906.0, 279.0, 76.0, 20.0 ],
					"frgb" : [ 0.658824, 0.658824, 0.658824, 1.0 ],
					"presentation" : 1,
					"id" : "obj-83",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s to_freeze",
					"patching_rect" : [ 797.0, 436.0, 70.0, 20.0 ],
					"id" : "obj-81",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"triangle" : 0,
					"presentation_rect" : [ 90.0, 55.0, 41.0, 20.0 ],
					"ignoreclick" : 1,
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 77.0, 168.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-80",
					"fontname" : "Arial",
					"numinlets" : 1,
					"bordercolor" : [ 0.94902, 0.94902, 0.94902, 0.0 ],
					"bgcolor" : [ 0.784314, 0.784314, 0.784314, 0.0 ],
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s to_attack",
					"patching_rect" : [ 984.0, 128.0, 69.0, 20.0 ],
					"id" : "obj-78",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r to_attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 82.0, 196.0, 67.0, 20.0 ],
					"id" : "obj-77",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 890.0, 45.0, 50.0, 20.0 ],
					"id" : "obj-76",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 412.0, 73.0, 50.0, 20.0 ],
					"id" : "obj-75",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 427.0, 204.0, 50.0, 20.0 ],
					"id" : "obj-73",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 812.0, 253.0, 50.0, 20.0 ],
					"id" : "obj-70",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r attack",
					"outlettype" : [ "" ],
					"patching_rect" : [ 645.0, 174.0, 50.0, 20.0 ],
					"id" : "obj-68",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s attack",
					"patching_rect" : [ 66.0, 251.0, 52.0, 20.0 ],
					"id" : "obj-66",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s to_freeze",
					"patching_rect" : [ 608.0, 561.0, 70.0, 20.0 ],
					"id" : "obj-61",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s to_freeze",
					"patching_rect" : [ 861.0, 553.0, 70.0, 20.0 ],
					"id" : "obj-58",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "slider",
					"presentation_rect" : [ 17.0, 526.0, 91.0, 19.0 ],
					"outlettype" : [ "" ],
					"mult" : 0.01,
					"floatoutput" : 1,
					"patching_rect" : [ 861.0, 473.0, 91.0, 19.0 ],
					"presentation" : 1,
					"id" : "obj-57",
					"relative" : 1,
					"numinlets" : 1,
					"bordercolor" : [ 1.0, 1.0, 1.0, 0.47451 ],
					"bgcolor" : [ 0.223529, 0.223529, 0.223529, 0.0 ],
					"size" : 100.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "pitch 0.666667, offset 0, ranpitch 0, randur 0, loop 0, stereo 0.7, right 1",
					"linecount" : 2,
					"outlettype" : [ "" ],
					"patching_rect" : [ 195.0, 562.0, 292.0, 32.0 ],
					"id" : "obj-56",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 861.0, 501.0, 50.0, 20.0 ],
					"id" : "obj-52",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "r to_freeze",
					"outlettype" : [ "" ],
					"patching_rect" : [ 68.0, 292.0, 68.0, 20.0 ],
					"id" : "obj-33",
					"fontname" : "Arial",
					"numinlets" : 0,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "s to_freeze",
					"patching_rect" : [ 195.0, 604.0, 70.0, 20.0 ],
					"id" : "obj-26",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "umenu",
					"presentation_rect" : [ 14.0, 160.0, 69.0, 20.0 ],
					"items" : [ "off", ",", "synced", ",", "random" ],
					"outlettype" : [ "int", "", "" ],
					"patching_rect" : [ 529.0, 410.0, 66.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-24",
					"fontname" : "Arial",
					"arrowbgcolor" : [ 0.266667, 0.266667, 0.266667, 0.0 ],
					"types" : [  ],
					"framecolor" : [ 0.066667, 0.066667, 0.066667, 0.0 ],
					"arrowframe" : 0,
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "switch 2",
					"outlettype" : [ "" ],
					"patching_rect" : [ 608.0, 411.0, 54.0, 20.0 ],
					"id" : "obj-2",
					"fontname" : "Arial",
					"numinlets" : 3,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "number",
					"presentation_rect" : [ 21.0, 231.0, 50.0, 20.0 ],
					"outlettype" : [ "int", "bang" ],
					"patching_rect" : [ 506.0, 229.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-74",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "10000",
					"outlettype" : [ "" ],
					"patching_rect" : [ 489.0, 137.0, 44.0, 18.0 ],
					"id" : "obj-72",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 21.0, 257.0, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 465.0, 285.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-67",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "* 2.",
					"outlettype" : [ "float" ],
					"patching_rect" : [ 427.0, 285.0, 32.5, 20.0 ],
					"id" : "obj-65",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "xrandint 1 6",
					"outlettype" : [ "int", "" ],
					"patching_rect" : [ 427.0, 229.0, 73.0, 20.0 ],
					"id" : "obj-64",
					"fontname" : "Arial",
					"numinlets" : 4,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "t b f",
					"outlettype" : [ "bang", "float" ],
					"patching_rect" : [ 427.0, 337.0, 33.0, 20.0 ],
					"id" : "obj-63",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 21.0, 283.0, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 427.0, 311.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-62",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "/ 1.",
					"outlettype" : [ "float" ],
					"patching_rect" : [ 412.0, 366.0, 33.0, 20.0 ],
					"id" : "obj-60",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 94.0, 205.0, 65.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 337.0, 215.0, 65.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-59",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "t f b",
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 412.0, 129.0, 33.0, 20.0 ],
					"id" : "obj-54",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "f",
					"outlettype" : [ "float" ],
					"patching_rect" : [ 489.0, 162.0, 33.0, 20.0 ],
					"id" : "obj-53",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "minimum 10000",
					"outlettype" : [ "int", "int" ],
					"patching_rect" : [ 412.0, 187.0, 96.0, 20.0 ],
					"id" : "obj-48",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "timer",
					"outlettype" : [ "float", "" ],
					"patching_rect" : [ 412.0, 101.0, 37.0, 20.0 ],
					"id" : "obj-47",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "xrand 10 500",
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 645.0, 200.0, 81.0, 20.0 ],
					"id" : "obj-32",
					"fontname" : "Arial",
					"numinlets" : 4,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 25.0, 444.25, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 849.0, 281.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-30",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "del",
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 812.0, 280.0, 33.0, 20.0 ],
					"id" : "obj-31",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 25.0, 468.25, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 852.0, 335.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-28",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "button",
					"presentation_rect" : [ 29.0, 85.0, 51.0, 51.0 ],
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 890.0, 74.0, 51.0, 51.0 ],
					"presentation" : 1,
					"id" : "obj-19",
					"numinlets" : 1,
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"outlinecolor" : [ 0.0, 0.0, 0.0, 0.094118 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"presentation_rect" : [ 25.0, 492.25, 50.0, 20.0 ],
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 608.0, 437.0, 50.0, 20.0 ],
					"presentation" : 1,
					"id" : "obj-14",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "/ 2972.",
					"outlettype" : [ "float" ],
					"patching_rect" : [ 608.0, 463.0, 47.0, 20.0 ],
					"id" : "obj-91",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "toggle",
					"presentation_rect" : [ 54.0, 420.25, 20.0, 20.0 ],
					"outlettype" : [ "int" ],
					"patching_rect" : [ 789.0, 336.0, 20.0, 20.0 ],
					"presentation" : 1,
					"checkedcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"id" : "obj-86",
					"numinlets" : 1,
					"bordercolor" : [ 1.0, 1.0, 1.0, 0.490196 ],
					"bgcolor" : [ 0.466667, 0.466667, 0.466667, 0.0 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "gate",
					"outlettype" : [ "" ],
					"patching_rect" : [ 797.0, 362.0, 34.0, 20.0 ],
					"id" : "obj-84",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "!- 1.",
					"outlettype" : [ "float" ],
					"patching_rect" : [ 608.0, 513.0, 33.0, 20.0 ],
					"id" : "obj-71",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "pipe",
					"outlettype" : [ "" ],
					"patching_rect" : [ 812.0, 334.0, 33.0, 20.0 ],
					"id" : "obj-69",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "t 0 1",
					"outlettype" : [ "int", "int" ],
					"patching_rect" : [ 812.0, 305.0, 34.0, 20.0 ],
					"id" : "obj-50",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "flonum",
					"outlettype" : [ "float", "bang" ],
					"patching_rect" : [ 608.0, 488.0, 50.0, 20.0 ],
					"id" : "obj-44",
					"fontname" : "Arial",
					"numinlets" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "left $1",
					"outlettype" : [ "" ],
					"patching_rect" : [ 608.0, 537.0, 43.0, 18.0 ],
					"id" : "obj-45",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "toggle",
					"presentation_rect" : [ 54.0, 396.0, 20.0, 20.0 ],
					"outlettype" : [ "int" ],
					"patching_rect" : [ 797.0, 389.0, 20.0, 20.0 ],
					"presentation" : 1,
					"checkedcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"id" : "obj-25",
					"numinlets" : 1,
					"bordercolor" : [ 1.0, 1.0, 1.0, 0.490196 ],
					"bgcolor" : [ 0.466667, 0.466667, 0.466667, 0.0 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "freeze $1",
					"outlettype" : [ "" ],
					"patching_rect" : [ 797.0, 413.0, 61.0, 18.0 ],
					"id" : "obj-22",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "ezdac~",
					"presentation_rect" : [ 112.0, 585.5, 45.0, 45.0 ],
					"patching_rect" : [ 54.0, 503.5, 45.0, 45.0 ],
					"presentation" : 1,
					"id" : "obj-9",
					"numinlets" : 2,
					"numoutlets" : 0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "bargraf~",
					"prototypename" : "no_border_gain",
					"presentation_rect" : [ 80.0, 576.5, 21.0, 69.0 ],
					"outlettype" : [ "float", "float", "float" ],
					"patching_rect" : [ 107.0, 349.5, 20.0, 140.0 ],
					"presentation" : 1,
					"id" : "obj-7",
					"fontname" : "Arial",
					"border" : 0,
					"numinlets" : 2,
					"show_value" : 1,
					"fontsize" : 8.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "gain~",
					"presentation_rect" : [ 61.0, 576.5, 20.0, 69.0 ],
					"outlettype" : [ "signal", "int" ],
					"patching_rect" : [ 88.0, 349.5, 19.0, 140.0 ],
					"presentation" : 1,
					"id" : "obj-8",
					"numinlets" : 2,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "bargraf~",
					"prototypename" : "no_border_gain",
					"presentation_rect" : [ 39.0, 576.5, 21.0, 69.0 ],
					"outlettype" : [ "float", "float", "float" ],
					"patching_rect" : [ 66.0, 349.5, 20.0, 140.0 ],
					"presentation" : 1,
					"id" : "obj-6",
					"fontname" : "Arial",
					"border" : 0,
					"numinlets" : 2,
					"show_value" : 1,
					"fontsize" : 8.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "gain~",
					"presentation_rect" : [ 20.0, 576.5, 20.0, 69.0 ],
					"outlettype" : [ "signal", "int" ],
					"patching_rect" : [ 47.0, 349.5, 19.0, 140.0 ],
					"presentation" : 1,
					"id" : "obj-13",
					"numinlets" : 2,
					"numoutlets" : 2
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "vst~ \"GRM Freeze Stereo\"",
					"outlettype" : [ "signal", "signal", "", "list", "int", "" ],
					"patching_rect" : [ 45.0, 318.0, 152.0, 20.0 ],
					"id" : "obj-5",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 6,
					"save" : [ "#N", "vst~", "loaduniqueid", 0, "GRM Freeze Stereo", ";" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "bargraf~",
					"presentation_rect" : [ 20.0, 55.0, 65.0, 21.0 ],
					"outlettype" : [ "float", "float", "float" ],
					"patching_rect" : [ 77.0, 148.0, 124.0, 14.0 ],
					"presentation" : 1,
					"id" : "obj-4",
					"fontname" : "Arial",
					"border" : 0,
					"numinlets" : 2,
					"show_value" : 1,
					"fontsize" : 12.0,
					"numoutlets" : 3
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "toggle",
					"prototypename" : "transparent_white",
					"presentation_rect" : [ 19.0, 19.0, 20.0, 20.0 ],
					"outlettype" : [ "int" ],
					"patching_rect" : [ 45.0, 85.0, 20.0, 20.0 ],
					"presentation" : 1,
					"checkedcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"id" : "obj-23",
					"numinlets" : 1,
					"bordercolor" : [ 1.0, 1.0, 1.0, 0.490196 ],
					"bgcolor" : [ 0.466667, 0.466667, 0.466667, 0.0 ],
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "loop 1",
					"outlettype" : [ "" ],
					"patching_rect" : [ 111.0, 87.0, 43.0, 18.0 ],
					"id" : "obj-21",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "open",
					"outlettype" : [ "" ],
					"patching_rect" : [ 70.0, 86.0, 37.0, 18.0 ],
					"id" : "obj-18",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "newobj",
					"text" : "sfplay~ 2",
					"outlettype" : [ "signal", "signal", "bang" ],
					"patching_rect" : [ 45.0, 114.0, 59.0, 20.0 ],
					"id" : "obj-15",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 3,
					"save" : [ "#N", "sfplay~", "", 2, 120960, 0, "", ";" ]
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "open",
					"presentation_rect" : [ 29.0, 551.0, 37.0, 18.0 ],
					"outlettype" : [ "" ],
					"patching_rect" : [ 10.0, 290.0, 37.0, 18.0 ],
					"presentation" : 1,
					"id" : "obj-11",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "message",
					"text" : "wet/dry $1",
					"outlettype" : [ "" ],
					"patching_rect" : [ 861.0, 528.0, 66.0, 18.0 ],
					"id" : "obj-49",
					"fontname" : "Arial",
					"numinlets" : 2,
					"fontsize" : 12.0,
					"numoutlets" : 1
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "panel",
					"presentation_rect" : [ 12.0, 182.0, 153.0, 128.0 ],
					"patching_rect" : [ 597.0, 77.0, 25.0, 23.0 ],
					"presentation" : 1,
					"id" : "obj-128",
					"grad1" : [ 0.341176, 0.0, 0.0, 1.0 ],
					"numinlets" : 1,
					"bordercolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.396078 ],
					"grad2" : [ 0.0, 0.0, 0.0, 1.0 ],
					"numoutlets" : 0,
					"angle" : 218.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "panel",
					"presentation_rect" : [ 12.0, 314.0, 153.0, 71.0 ],
					"patching_rect" : [ 860.0, 196.0, 25.0, 23.0 ],
					"presentation" : 1,
					"id" : "obj-129",
					"grad1" : [ 0.317647, 0.0, 0.0, 1.0 ],
					"numinlets" : 1,
					"bordercolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.396078 ],
					"grad2" : [ 0.0, 0.0, 0.0, 1.0 ],
					"numoutlets" : 0,
					"angle" : 238.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "panel",
					"presentation_rect" : [ 12.0, 50.0, 153.0, 108.0 ],
					"patching_rect" : [ 952.0, 79.0, 25.0, 23.0 ],
					"presentation" : 1,
					"id" : "obj-138",
					"grad1" : [ 0.223529, 0.0, 0.0, 1.0 ],
					"numinlets" : 1,
					"bordercolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.396078 ],
					"grad2" : [ 0.0, 0.0, 0.0, 1.0 ],
					"numoutlets" : 0,
					"angle" : 218.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "panel",
					"presentation_rect" : [ 12.0, 13.0, 153.0, 32.0 ],
					"patching_rect" : [ 234.0, 82.0, 25.0, 23.0 ],
					"presentation" : 1,
					"id" : "obj-140",
					"grad1" : [ 0.184314, 0.0, 0.0, 1.0 ],
					"numinlets" : 1,
					"bordercolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.396078 ],
					"grad2" : [ 0.0, 0.0, 0.0, 1.0 ],
					"numoutlets" : 0,
					"angle" : 218.0
				}

			}
, 			{
				"box" : 				{
					"maxclass" : "panel",
					"presentation_rect" : [ 12.0, 389.0, 153.0, 132.0 ],
					"patching_rect" : [ 887.0, 377.0, 25.0, 23.0 ],
					"presentation" : 1,
					"id" : "obj-141",
					"grad1" : [ 0.317647, 0.0, 0.0, 1.0 ],
					"numinlets" : 1,
					"bordercolor" : [ 0.0, 0.0, 0.0, 0.0 ],
					"bgcolor" : [ 0.0, 0.0, 0.0, 0.396078 ],
					"grad2" : [ 0.0, 0.0, 0.0, 1.0 ],
					"numoutlets" : 0,
					"angle" : 238.0
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"source" : [ "obj-20", 0 ],
					"destination" : [ "obj-12", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-12", 0 ],
					"destination" : [ "obj-78", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-15", 0 ],
					"destination" : [ "obj-1", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-1", 1 ],
					"destination" : [ "obj-66", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-145", 0 ],
					"destination" : [ "obj-64", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-74", 0 ],
					"destination" : [ "obj-145", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-64", 0 ],
					"destination" : [ "obj-65", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-73", 0 ],
					"destination" : [ "obj-64", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-32", 0 ],
					"destination" : [ "obj-2", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-68", 0 ],
					"destination" : [ "obj-32", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-95", 0 ],
					"destination" : [ "obj-32", 2 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-94", 0 ],
					"destination" : [ "obj-32", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-132", 1 ],
					"destination" : [ "obj-32", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-62", 0 ],
					"destination" : [ "obj-63", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-65", 0 ],
					"destination" : [ "obj-62", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-67", 0 ],
					"destination" : [ "obj-65", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-15", 1 ],
					"destination" : [ "obj-5", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-23", 0 ],
					"destination" : [ "obj-15", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-18", 0 ],
					"destination" : [ "obj-15", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-21", 0 ],
					"destination" : [ "obj-15", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-15", 0 ],
					"destination" : [ "obj-5", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-15", 0 ],
					"destination" : [ "obj-4", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-25", 0 ],
					"destination" : [ "obj-22", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-84", 0 ],
					"destination" : [ "obj-25", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-86", 0 ],
					"destination" : [ "obj-84", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-125", 0 ],
					"destination" : [ "obj-21", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-110", 0 ],
					"destination" : [ "obj-56", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-105", 0 ],
					"destination" : [ "obj-72", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-48", 0 ],
					"destination" : [ "obj-60", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-48", 0 ],
					"destination" : [ "obj-59", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-22", 0 ],
					"destination" : [ "obj-81", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-4", 0 ],
					"destination" : [ "obj-80", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-76", 0 ],
					"destination" : [ "obj-19", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-75", 0 ],
					"destination" : [ "obj-47", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-75", 0 ],
					"destination" : [ "obj-47", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-70", 0 ],
					"destination" : [ "obj-31", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-45", 0 ],
					"destination" : [ "obj-61", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-49", 0 ],
					"destination" : [ "obj-58", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-56", 0 ],
					"destination" : [ "obj-26", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-57", 0 ],
					"destination" : [ "obj-52", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-52", 0 ],
					"destination" : [ "obj-49", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-33", 0 ],
					"destination" : [ "obj-5", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-24", 0 ],
					"destination" : [ "obj-2", 0 ],
					"hidden" : 0,
					"midpoints" : [ 538.5, 441.0, 603.0, 441.0, 603.0, 401.0, 617.5, 401.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-60", 0 ],
					"destination" : [ "obj-2", 1 ],
					"hidden" : 0,
					"midpoints" : [ 421.5, 396.0, 635.0, 396.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-2", 0 ],
					"destination" : [ "obj-14", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-11", 0 ],
					"destination" : [ "obj-5", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-5", 0 ],
					"destination" : [ "obj-13", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-5", 1 ],
					"destination" : [ "obj-8", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-91", 0 ],
					"destination" : [ "obj-44", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-71", 0 ],
					"destination" : [ "obj-45", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-44", 0 ],
					"destination" : [ "obj-71", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-50", 1 ],
					"destination" : [ "obj-84", 1 ],
					"hidden" : 0,
					"midpoints" : [ 836.5, 329.0, 847.0, 329.0, 847.0, 359.0, 821.5, 359.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-69", 0 ],
					"destination" : [ "obj-84", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-50", 0 ],
					"destination" : [ "obj-69", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-13", 0 ],
					"destination" : [ "obj-6", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-8", 0 ],
					"destination" : [ "obj-7", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-13", 0 ],
					"destination" : [ "obj-9", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-8", 0 ],
					"destination" : [ "obj-9", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-13", 1 ],
					"destination" : [ "obj-8", 0 ],
					"hidden" : 1,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-14", 0 ],
					"destination" : [ "obj-91", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-28", 0 ],
					"destination" : [ "obj-69", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-30", 0 ],
					"destination" : [ "obj-31", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-31", 0 ],
					"destination" : [ "obj-50", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-47", 0 ],
					"destination" : [ "obj-54", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-54", 1 ],
					"destination" : [ "obj-53", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-54", 0 ],
					"destination" : [ "obj-48", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-48", 0 ],
					"destination" : [ "obj-53", 1 ],
					"hidden" : 0,
					"midpoints" : [ 421.5, 217.0, 525.0, 217.0, 525.0, 157.0, 512.5, 157.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-53", 0 ],
					"destination" : [ "obj-48", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-63", 1 ],
					"destination" : [ "obj-60", 1 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-63", 0 ],
					"destination" : [ "obj-60", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-72", 0 ],
					"destination" : [ "obj-53", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-3", 0 ],
					"destination" : [ "obj-23", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-3", 2 ],
					"destination" : [ "obj-15", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-3", 1 ],
					"destination" : [ "obj-105", 0 ],
					"hidden" : 0,
					"midpoints" : [ 77.0, 56.0, 465.0, 56.0, 465.0, 108.0, 498.5, 108.0 ]
				}

			}
, 			{
				"patchline" : 				{
					"source" : [ "obj-16", 0 ],
					"destination" : [ "obj-3", 0 ],
					"hidden" : 0,
					"midpoints" : [  ]
				}

			}
 ]
	}

}
