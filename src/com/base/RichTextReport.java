package com.base;
/*
*	Rich Text Report for displaying handicap for truline2000.
*
*/
import com.base.Bris;
import com.base.BrisMCP;
import com.base.Handicap;
import com.base.Launch;
import com.base.Lib;
import com.base.Log;
import com.base.Post;
import com.base.Race;
import com.base.TrainerJockeyStats;

import java.util.*;
import java.io.*;
import java.text.*;

import com.mains.Truline;

public class RichTextReport
{
 public int             fontsize;
 // public int pagesize;
 public static String[] names        = { "FS", "SS", "FT", "TT", "CS", "AS",
  "RE", "QP", "EN", "EPS"          };
 public int[]           biasPoints   = new int[names.length]; // Bias points Dirt
 public int[]           biasPointsT   = new int[names.length]; // Bias points Turf
private String         finishPosPrt = "";
private String         raceSurface = "";
public RichTextReport() {
}
/**
 * Generate the report - all races - Bris DRF input
 */
public void generate(String filename, Bris bris, boolean print)
{
 for (int i = 0; i < 10; i++) {
  biasPoints[i] = 0;
  biasPointsT[i] = 0;
 }
 boolean generated = false;
 String name = "tmp.rtf";
 if (!print) {
  name = filename + ".rtf";
  Truline.println("Generating Text Report to " + name);
 }
 try {
  if (Log.isDebug(Log.TRACE))
   Log.print("Writing text report to " + name + "\n");
  PrintWriter out = new PrintWriter(new FileWriter(name), true);
  fontSetup(out);
  for (Enumeration e = bris.m_races.elements(); e.hasMoreElements();) {
   Race race = (Race) e.nextElement();
   if (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0")))
   {
    writeReport(out, race);
    accumulateBias(race);
   }
   generated = true;
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception opening output file " + e + "\n");
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0)
   Log.println(results.toString());
 }
}
/**
 * Generate the report - all races - Bris MCP input
 */
public void generate(String filename, BrisMCP brisMCP, boolean print)
{
 for (int i = 0; i < 10; i++) {
  biasPoints[i] = 0;
  biasPointsT[i] = 0;
 }
 boolean generated = false;
 String name = "tmp.rtf";
 if (!print) {
  name = filename + ".rtf";
  Truline.println("Generating Text Report to " + name);
 }
 try {
  if (Log.isDebug(Log.TRACE))
   Log.print("Writing text report to " + name + "\n");
  PrintWriter out = new PrintWriter(new FileWriter(name), true);
  fontSetup(out);
  for (Enumeration e = brisMCP.m_races.elements(); e.hasMoreElements();) {
   Race race = (Race) e.nextElement();
   if (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0")))
   {
    writeReport(out, race);
    accumulateBias(race);
   }
   generated = true;
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception opening output file " + e + "\n");
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0)
   Log.println(results.toString());
 }
}
/**
 * Generate the report.
 * 
 * @param filename
 *         - display name of the BRIS file
 * @param bris
 *         - handicap structure
 * @param raceNbr
 *         - The requested race number, (-1 for all races)
 * @param print
 *         - flag indicating wether to print or just generate file
 */
public void generate(String filename, Bris bris, int raceNbr, boolean print)
{
 String name = "truline.rtf";
 if (!print) {
  if (raceNbr > 0)
   name = filename + "_" + raceNbr + ".rtf";
  else
   name = filename + ".rtf";
  Truline.println("Generating Text Report to " + name);
 }
 for (int i = 0; i < 10; i++)
  biasPoints[i] = 0;
 boolean generated = false;
 PrintWriter out = null;
 try {
  out = new PrintWriter(new FileWriter(name));
  fontSetup(out);
  for (int idx = 0; idx < bris.m_races.size(); idx++) {
   Race race = (Race) bris.m_races.elementAt(idx);
   if (race.m_raceNo == raceNbr) 
    raceSurface = race.m_surface;
  }
  for (int idx = 0; idx < bris.m_races.size(); idx++) {
   Race race = (Race) bris.m_races.elementAt(idx);
   if (raceNbr > 0) {
    // just one race requested.
    if (generated)
     break;
    if (race.m_raceNo != raceNbr) {
     if (race.m_surface.equals(raceSurface) && (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0"))))
      accumulateBias(race);
     continue;
    }
   }
   if (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0")))
   {
    writeReport(out, race);
    accumulateBias(race);
   }
   generated = true;
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception generating report " + e + "\n");
  Truline.println("Exception generating report " + e);
  if (out != null)
   out.close();
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   // launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0) {
   Log.println(results.toString());
   Truline.println(results.toString());
  }
 }
}
/**
 * Generate the report.
 * 
 * @param filename
 *         - display name of the BRIS MCP file
 * @param brisMCP
 *         - handicap structure
 * @param raceNbr
 *         - The requested race number, (-1 for all races)
 * @param print
 *         - flag indicating wether to print or just generate file
 */
public void generate(String filename, BrisMCP brisMCP, int raceNbr,
  boolean print)
{
 String name = "truline.rtf";
 if (!print) {
  if (raceNbr > 0)
   name = filename + "_" + raceNbr + ".rtf";
  else
   name = filename + ".rtf";
  Truline.println("Generating Text Report to " + name);
 }
 for (int i = 0; i < 10; i++)
  biasPoints[i] = 0;
 boolean generated = false;
 PrintWriter out = null;
 try {
  out = new PrintWriter(new FileWriter(name));
  fontSetup(out);
  for (int idx = 0; idx < brisMCP.m_races.size(); idx++) {
   Race race = (Race) brisMCP.m_races.elementAt(idx);
   if (race.m_raceNo == raceNbr) 
    raceSurface = race.m_surface;
  }
  for (int idx = 0; idx < brisMCP.m_races.size(); idx++) {
   Race race = (Race) brisMCP.m_races.elementAt(idx);
   if (raceNbr > 0) {
    // just one race requested.
    if (generated)
     break;
    if (race.m_raceNo != raceNbr) {
     if (race.m_surface.equals(raceSurface) && (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0"))))
      accumulateBias(race);
     continue;
    }
   }
   if (race.m_distance >= Lib.atoi(Truline.userProps.getProperty("MinDistance", "0")))
   {
    writeReport(out, race);
    accumulateBias(race);
   }
   generated = true;
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception generating report " + e + "\n");
  Truline.println("Exception generating report " + e);
  if (out != null)
   out.close();
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   // launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0) {
   Log.println(results.toString());
   Truline.println(results.toString());
  }
 }
}
/**
 * Generate the Horse Flow report - all races - Bris DRF input
 */
public void generateHF(String filename, Bris bris, boolean print)
{
 boolean generated = false;
 String name = filename + "HF.rtf";
 if (!print) {
  name = filename + "HF.rtf";
  Truline.println("Generating Horse Flow Text Report to " + name);
 }
 try {
  if (Log.isDebug(Log.TRACE))
   Log.print("Writing text report to " + name + "\n");
  PrintWriter out = new PrintWriter(new FileWriter(name), true);
  fontSetup(out);
  for (Enumeration e = bris.m_races.elements(); e.hasMoreElements();) {
   Race race = (Race) e.nextElement();
   if (race.cntHorseFlows > 0) {
    writeHFReport(out, race);
    generated = true;
   }
  }
  if (!generated) {
   generated = true;
   out.println("\\par                          " + Truline.title
     + "                ");
   out.println("\\par [" + Truline.version + "] " + Truline.copyright);
   out
     .println("\\par ======================================+=====================================================");
   out.println("\\par TrackDate " + filename + "    No Horse Flows today");
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception opening output file " + e + "\n");
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0)
   Log.println(results.toString());
 }
}
/**
 * Generate the report - all races - Bris MCP input
 */
public void generateHF(String filename, BrisMCP brisMCP, boolean print)
{
 boolean generated = false;
 String name = filename + "HF.rtf";
 if (!print) {
  name = filename + "HF.rtf";
  Truline.println("Generating Horse Flow Text Report to " + name);
 }
 try {
  if (Log.isDebug(Log.TRACE))
   Log.print("Writing text report to " + name + "\n");
  PrintWriter out = new PrintWriter(new FileWriter(name), true);
  fontSetup(out);
  for (Enumeration e = brisMCP.m_races.elements(); e.hasMoreElements();) {
   Race race = (Race) e.nextElement();
   if (race.cntHorseFlows > 0) {
    writeHFReport(out, race);
    generated = true;
   }
  }
  if (!generated) {
   generated = true;
   out.println("\\par                          " + Truline.title
     + "                ");
   out.println("\\par [" + Truline.version + "] " + Truline.copyright);
   out
     .println("\\par ======================================+=====================================================");
   out.println("\\par TrackDate " + filename + "    No Horse Flows today");
  }
  out.println("}");
  out.close();
 } catch (Exception e) {
  Log.print("Exception opening output file " + e + "\n");
 }
 if (generated && print) {
  // Send tmp.rpt to printer
  StringBuffer results = new StringBuffer();
  try {
   String printProgram = Truline.userProps.getProperty("PrintProgram",
     "Wordpad.exe /p");
   String[] command = Launch.fixArgs(printProgram + " " + name);
   Launch launcher = new Launch(command, null, null, results);
   launcher.exec();
   launcher.waitfor(); // wait until it completes.
  } catch (Exception e) {
   results.append(e.toString());
   results.append("\n");
  }
  if (results.length() > 0)
   Log.println(results.toString());
 }
}
public void accumulateBias(Race race)
{
 for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
  Post post = (Post) e.nextElement();
  String entry = post.m_props.getProperty("ENTRY", "");
  if (entry.equals("S")) {
   continue;
  }
  if (post.m_handicap == null || post.m_horseName == null)
   continue; // position is empty
  String finishPos = post.m_finishPos;
  if (finishPos.equals("1")) {
   for (int i = 0; i < 10; i++) {
    if ((Truline.userProps.getProperty("TrackTheBias", "N").equals("1") && post.m_handicap.rank[i] == 1)
      || (Truline.userProps.getProperty("TrackTheBias", "N").equals("2") && post.m_handicap.rank[i] < 3)) {
     if (race.m_surface.equals("D"))
       biasPoints[i] = biasPoints[i] + 2;
     else
      biasPointsT[i] = biasPointsT[i] + 2;
    }
    if ((Truline.userProps.getProperty("TrackTheBias", "N").equals("1") && post.m_handicap.rank[i] == 2)) {
     if (race.m_surface.equals("D"))
      biasPoints[i] = biasPoints[i] + 1;
    else
     biasPointsT[i] = biasPointsT[i] + 1;
    }
  }
  }
  if (finishPos.equals("2")) {
   for (int i = 0; i < 10; i++) {
    if ((Truline.userProps.getProperty("TrackTheBias", "N").equals("1") && post.m_handicap.rank[i] == 1)
      || (Truline.userProps.getProperty("TrackTheBias", "N").equals("2") && post.m_handicap.rank[i] < 3))
     if (race.m_surface.equals("D"))
      biasPoints[i] = biasPoints[i] + 1;
    else
     biasPointsT[i] = biasPointsT[i] + 1;
   }
  }
 }
}
private void fontSetup(PrintWriter out)
{
 out
   .print("{\\rtf1\\ansi\\deff0\\deftab720{\\fonttbl{\\f0\\fswiss MS Sans Serif;}{\\f1\\froman\\fcharset2 Symbol;}{\\f2\\fmodern Courier New;}{\\f3\fmodern\\fprq1 Courier New;}}");
 out.print("{\\colortbl\\red0\\green0\\blue0;}");
 out
   .print("\\deflang1033\\margl1440\\margr1440\\margt864\\margb864\\pard\\plain");
 fontsize = Lib.atoi(Truline.userProps.getProperty("FontSize", "8"));
 switch (fontsize) {
  case 7:
   out.print("\\f2\\fs14\\cf0");
   // pagesize = 70;
   break;
  case 9:
   out.print("\\f2\\fs18\\cf0");
   // pagesize = 61;
   break;
  default:
  case 8:
   out.print("\\f2\\fs16\\cf0");
   // pagesize = 67;
   break;
 }
 // pagesize = Lib.atoi(Truline.userProps.getProperty("PageSize",
 // ""+pagesize));
 // Truline.println("Font size="+fontsize+" Page size="+pagesize);
}
public void writeReport(PrintWriter out, Race race)
{
 DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
 DateFormat dtf = DateFormat.getDateTimeInstance(DateFormat.SHORT,
   DateFormat.SHORT);
 String datestr = dtf.format(new Date());
 int line = 0;
 String biasPts[] = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
 " " }; // Bias points for each horse
int biasBonus = 0;
 try {
  out.println("\\par                          " + Truline.title
    + "                " + datestr);
  out.println("\\par [" + Truline.version + "] " + Truline.copyright);
  out
    .println("\\par ======================================+=====================================================");
  out.println("\\par Track "
    + Lib.pad(race.m_track, 3)
    + "    "
    + Lib.datetoa(race.m_raceDate)
    + "    Race#"
    + race.m_raceNo
    + "   Distance "
    + Lib.ftoa(((double) race.m_distance) / Handicap.YdPerF, 1)
    + "F"
    + "  Type "
    + ((race.m_raceType.equals("G1")) ? "G1-Stake I" : (race.m_raceType
      .equals("G2")) ? "G2-Stake II"
      : (race.m_raceType.equals("G3")) ? "G3-Stake III" : (race.m_raceType
        .equals("N")) ? "N-nongraded stake"
        : (race.m_raceType.equals("A")) ? "A-allowance" : (race.m_raceType
          .equals("R")) ? "R-Starter Alw"
          : (race.m_raceType.equals("T")) ? "T-Starter Hcp" : (race.m_raceType
            .equals("C")) ? "C-claiming"
            : (race.m_raceType.equals("S")) ? "S-mdn sp wt" : (race.m_raceType
              .equals("M")) ? "M-mdn claimer" : race.m_raceType));
  NumberFormat fmt = NumberFormat.getCurrencyInstance();
  out.println("\\par "
    + ((race.m_claim != 0) ? ("Claim " + fmt.format(race.m_claim))
      : (race.m_purse != 0) ? ("Purse " + fmt.format(race.m_purse))
        : "                ") + "  Surface "
    + ((race.m_surface.equals("D")) ? "Dirt" : "Turf"));
  String sexAge = race.m_props.getProperty("AGESEX", "");
  out.print("\\par AGE/SEX (" + sexAge + ")");
  switch (sexAge.charAt(0)) {
   case 'A':
    out.print(" 2 year olds");
    break;
   case 'B':
    out.print(" 3 year olds");
    break;
   case 'C':
    out.print(" 4 year olds");
    break;
   case 'D':
    out.print(" 5 year olds");
    break;
   case 'E':
    out.print(" 3 & 4 year olds");
    break;
   case 'F':
    out.print(" 4 & 5 year olds");
    break;
   case 'G':
    out.print(" 3, 4, and 5 year olds");
    break;
   case 'H':
    out.print(" all ages");
    break;
  }
  switch (sexAge.charAt(1)) {
   case 'O':
    out.print(", That age Only");
    break;
   case 'U':
    out.print(", That age and Up");
    break;
  }
  switch (sexAge.charAt(2)) {
   case 'N':
    out.print(", No Sex Restrictions");
    break;
   case 'M':
    out.print(", Mares and Fillies");
    break;
   case 'C':
    out.print(", Colts and/or Geldings");
    break;
   case 'F':
    out.print(", Fillies Only");
    break;
  }
  out.println();
  if (Truline.userProps.getProperty("Experimental", "N").equals("Y")) {
   int cnt = 0;
   while (cnt <= race.cntRaceFlows) {
    out.println("\\par " + race.raceFlows[cnt]);
    cnt++;
   }
  }
  out
    .println("\\par =============================================================================================");
  out
    .println("\\par     #  Horse             RR      EPS  EN    FS   TT    SS   CS    FT  AS  RE QP TP  ML  ODDS");
  if (Truline.userProps.getProperty("TrackTheBias", "N").equals("1")
    || Truline.userProps.getProperty("TrackTheBias", "N").equals("2")) {
   biasBonus = 0;
   for (int i = 0; i < 10; i++) {
    if (race.m_surface.equals("D")) {
      biasPts[i] = "" + biasPoints[i];
      biasBonus = biasBonus + biasPoints[i];
     }
    else {
      biasPts[i] = "" + biasPointsT[i];
      biasBonus = biasBonus + biasPointsT[i];
     }
   }
   biasPts[10] = "" + biasBonus;
   out
   .println("\\par        Total Bias                "
     + Lib.rjust(biasPts[Handicap.EPS], 3)
     + Lib.rjust(biasPts[Handicap.EN], 4) + Lib.rjust(biasPts[Handicap.FS], 6)
     + Lib.rjust(biasPts[Handicap.TT], 5) + Lib.rjust(biasPts[Handicap.SS], 6)
     + Lib.rjust(biasPts[Handicap.CS], 5) + Lib.rjust(biasPts[Handicap.FT], 6)
     + Lib.rjust(biasPts[Handicap.AS], 4) + Lib.rjust(biasPts[Handicap.RE], 4)
     + Lib.rjust(biasPts[Handicap.QP], 3) + Lib.rjust(biasPts[10], 3)
     );
  }
  out.println("\\par ");
  line += 9;
  // Display each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   biasBonus = 0;
   biasPts[10] = "";
   Post post = (Post) e.nextElement();
   String entry = post.m_props.getProperty("ENTRY", "");
   if (entry.equals("S")) {
    out.println("\\par    " + Lib.pad(post.cloth, 4)
      + Lib.pad(post.m_horseName, 16) + "  SCRATCHED");
    line++;
    continue;
   }
   if (post.m_handicap == null || post.m_horseName == null)
    continue; // position is empty
   String finishPos = post.m_finishPos;
   if (finishPos.equals("1") || finishPos.equals("2") || finishPos.equals("3")
     || finishPos.equals("4"))
    finishPosPrt = "(" + finishPos + ")";
   else
    finishPosPrt = "";
   if (Truline.userProps.getProperty("TrackTheBias", "N").equals("1")
     || Truline.userProps.getProperty("TrackTheBias", "N").equals("2")) {
    // See if horse gets any bias points
    biasBonus = 0;
    for (int i = 0; i < 10; i++) {
     if (race.m_surface.equals("D")) {
      biasPts[i] = "";
      if (post.m_handicap.rank[i] == 1 && biasPoints[i] > 0) {
       biasPts[i] = "" + biasPoints[i];
       biasBonus = biasBonus + biasPoints[i];
      }
      if (Truline.userProps.getProperty("TrackTheBias", "N").equals("2")
        && post.m_handicap.rank[i] == 2 && biasPoints[i] > 0) {
       biasPts[i] = "" + biasPoints[i];
       biasBonus = biasBonus + biasPoints[i];
      }
     }
     else {
      biasPts[i] = "";
      if (post.m_handicap.rank[i] == 1 && biasPointsT[i] > 0) {
       biasPts[i] = "" + biasPointsT[i];
       biasBonus = biasBonus + biasPointsT[i];
      }
      if (Truline.userProps.getProperty("TrackTheBias", "N").equals("2")
        && post.m_handicap.rank[i] == 2 && biasPoints[i] > 0) {
       biasPts[i] = "" + biasPointsT[i];
       biasBonus = biasBonus + biasPointsT[i];
      }
     }
    }
    if (biasBonus > 0) {
     biasPts[10] = "" + biasBonus;
     biasPts[11] = "Bias";
     post.m_bias = "Bias=" + biasBonus;
    }
    else {
     biasPts[10] = "";
     biasPts[11] = "";
    }
     
   }
   String repRaceDate;
   if (post.m_handicap.m_repRace != null)
    repRaceDate = Lib.datetoa(post.m_handicap.m_repRace.ppRaceDate);
   else
    repRaceDate = "00/00";
   out.println("\\par " + Lib.pad(post.m_sireTS, 1)
     + Lib.pad(post.m_sireTS2, 1) + Lib.pad(post.m_ownerTrn, 1)
     + Lib.pad(post.cloth, 4) + Lib.pad(post.m_horseNameP, 18)
     + Lib.pad(repRaceDate, 5)
     + Lib.rjust(post.m_handicap.value[Handicap.EPS], 6)
     + Lib.rjust(post.m_handicap.value[Handicap.EN], 4, 1)
     + ((post.m_handicap.extraFlg) ? "#" : " ")
     + Lib.rjust(post.m_handicap.value[Handicap.FS], 5, 1)
     + Lib.rjust(post.m_handicap.value[Handicap.TT], 5, 1)
     + Lib.rjust(post.m_handicap.value[Handicap.SS], 6, 1)
     + Lib.rjust(post.m_handicap.value[Handicap.CS], 5, 1)
     + Lib.rjust(post.m_handicap.value[Handicap.FT], 6, 1)
     + Lib.rjust(post.m_handicap.value[Handicap.AS], 4)
     + Lib.rjust(post.m_handicap.value[Handicap.RE], 4)
     + Lib.rjust(post.m_handicap.value[Handicap.QP], 3)
     + Lib.rjust(post.m_handicap.bonus + post.m_handicap.points, 3)
     + Lib.rjust(post.m_morningLine, 5) + Lib.rjust(post.m_odds, 6));
   line++;
   int tstart, tplace, twin, jstart, jplace, jwin;
   tstart = Lib.atoi(post.m_props.getProperty("TRAINERSTARTS"));
   tplace = Lib.atoi(post.m_props.getProperty("TRAINERPLACES"));
   twin = Lib.atoi(post.m_props.getProperty("TRAINERWINS"));
   jstart = Lib.atoi(post.m_props.getProperty("JOCKEYSTARTS"));
   jplace = Lib.atoi(post.m_props.getProperty("JOCKEYPLACES"));
   jwin = Lib.atoi(post.m_props.getProperty("JOCKEYWINS"));
/*
   int jpcnt = (jstart > 0) ? (jwin + jplace) * 100 / jstart : 0;
   int tpcnt = (tstart > 0) ? (twin + tplace) * 100 / tstart : 0;
*/   
   int jpcnt = (jstart > 0) ? (jwin) * 100 / jstart : 0;
   int tpcnt = (tstart > 0) ? (twin) * 100 / tstart : 0;
   out.println("\\par   " + Lib.pad(post.m_betfactorsPR, 3)
     + Lib.pad(post.m_trainerNamePT, 2)
     + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
     + Lib.rjust((double) tstart, 4) + "/"
     + Lib.pad(Lib.ftoa((double) tpcnt, 0) + "%", 4)
     + Lib.rjust(post.m_handicap.rank[Handicap.EPS], 3)
     + Lib.rjust(post.m_handicap.rank[Handicap.EN], 4)
     + Lib.rjust(post.m_handicap.rank[Handicap.FS], 6)
     + Lib.rjust(post.m_handicap.rank[Handicap.TT], 5)
     + Lib.rjust(post.m_handicap.rank[Handicap.SS], 6)
     + Lib.rjust(post.m_handicap.rank[Handicap.CS], 5)
     + Lib.rjust(post.m_handicap.rank[Handicap.FT], 6)
     + Lib.rjust(post.m_handicap.rank[Handicap.AS], 4)
     + Lib.rjust(post.m_handicap.rank[Handicap.RE], 4)
     + Lib.rjust(post.m_handicap.rank[Handicap.QP], 3)
     + Lib.rjust(post.m_handicap.bonusRank, 3) + Lib.rjust(finishPosPrt, 5));
   out.println("\\par " + Lib.pad(post.m_trnJkyPct, 7)
     + Lib.pad(post.m_props.getProperty("JOCKEY", "").toLowerCase(), 17)
     + Lib.rjust((double) jstart, 4) + "/"
     + Lib.pad(Lib.ftoa((double) jpcnt, 0) + "%", 4)
     + Lib.rjust(biasPts[Handicap.EPS], 3)
     + Lib.rjust(biasPts[Handicap.EN], 4) + Lib.rjust(biasPts[Handicap.FS], 6)
     + Lib.rjust(biasPts[Handicap.TT], 5) + Lib.rjust(biasPts[Handicap.SS], 6)
     + Lib.rjust(biasPts[Handicap.CS], 5) + Lib.rjust(biasPts[Handicap.FT], 6)
     + Lib.rjust(biasPts[Handicap.AS], 4) + Lib.rjust(biasPts[Handicap.RE], 4)
     + Lib.rjust(biasPts[Handicap.QP], 3) + Lib.rjust(biasPts[10], 3)
     + Lib.rjust(biasPts[11], 5));
   line++;
  }
  out
    .println("\\par  # = Must Bet Energy or Power Trainer / $ = Turf Sire / d = Dam Sire / * = Trainer-Owner");
  out.println("\\par ");
  out
    .println("\\par ============================== Recap of Top Ranked Horses ===================================");
  if (Truline.userProps.getProperty("ShowTidbits", "N").equals("Y")) {
   if (race.m_bettable1 == "N")
    out.println("\\par *** NON-BETTABLE RACE ***");
   else if (race.m_bettable2 == "N")
   {
    out.println("*** LOW PROBABILITY RACE - " + race.m_cntnrl
      + " horses have no running lines ***");
    if (race.m_cnt1st > 0 && race.m_cntnrl > 0)
     out.println("\\par *** CAUTION - " + race.m_cnt1st + " first time starter(s)");
    }
   // else if (race.m_surface.equals("T"))
   // out.println("\\par *** CAUTION - Turf and "+race.m_cntnrl+" horses have no running lines ***");
   else if (race.m_cnthorses < 8)
   {
    out.println("\\par *** Double overlay betting only - " + race.m_cnthorses
      + " horses in race - " + race.m_cntnrl + " have no running line");
    if (race.m_cnt1st > 0 && race.m_cntnrl > 0)
     out.println("\\par *** CAUTION - " + race.m_cnt1st + " first time starter(s)");
   }
   else if (race.m_cntnrl > 2)
   {
    out.println("\\par *** CAUTION - " + race.m_cntnrl + " horses have no running line");
    if (race.m_cnt1st > 0 && race.m_cntnrl > 0)
     out.println("\\par *** CAUTION - " + race.m_cnt1st + " first time starter(s)");
   }
   else if (race.m_cnt1st > 0)
    out.println("\\par *** CAUTION - " + race.m_cnt1st + " first time starter(s)");
   else
    out.println("\\par *** PRIME BETTING RACE ***");
  }
  // Display body language hints
  if (Truline.userProps.getProperty("ShowBodyLanguage", "N").equals("Y")) {
   switch (sexAge.charAt(0)) {
    case 'A':
     out.print("\\par 2 year old ");
     switch (sexAge.charAt(2)) {
      case 'N':
       out.println("Males B/L = Calm / Fillies = Calm and Fat");
       break;
      case 'C':
       out.println("Males B/L = Calm");
       break;
      case 'F':
       out.println("Fillies B/L = Calm and Fat");
       break;
     }
     break;
    case 'B':
    case 'E':
    case 'G':
    case 'H':
     out.print("\\par 3 year old ");
     switch (sexAge.charAt(2)) {
      case 'N':
       out.println("Males B/L = Calm / Fillies = Calm and Fat");
       break;
      case 'C':
       out.println("Males B/L = Calm");
       break;
      case 'M':
      case 'F':
       out.println("Fillies B/L = Calm and Fat");
       break;
     }
     switch (sexAge.charAt(1)) {
      case 'O':
       break;
      case 'U':
       out.print("\\par 4 year old and up ");
       switch (sexAge.charAt(2)) {
        case 'N':
         out.println("Males B/L = Prancing / Mares B/L = Prancing and Fat");
         break;
        case 'C':
         out.println("Males B/L = Prancing");
         break;
        case 'M':
        case 'F':
         out.println("Mares B/L = Prancing and Fat");
         break;
       }
       break;
     }
     break;
    case 'C':
    case 'D':
    case 'F':
     out.print("\\par 4 year old and up ");
     switch (sexAge.charAt(2)) {
      case 'N':
       out.println("Males B/L = Prancing / Mares B/L = Prancing and Fat");
       break;
      case 'C':
       out.println("Males B/L = Prancing");
       break;
      case 'M':
      case 'F':
       out.println("Mares B/L = Prancing and Fat");
       break;
     }
     break;
   }
  }
  line += 3;
  String[] odds = { "8-5 ", "5-2 ", "6-1 ", "9-1 ", "20-1", "30-1" };
  int i = 0;
  int j = 0;
  int pts = 999;
  int ml;
  String DO;
  String Adv20;
  for (Enumeration e = race.ranking.elements(); e.hasMoreElements();) {
   if (i >= 6) // show only the first 6
    break;
   if (i == 1) {
    out.print("\\par Track Condition ___________ ");
    line++;
   } else if (i == 4) {
    out.print("\\par Final Fraction  ___________ ");
    line++;
   } else {
    out.print("\\par                             ");
    line++;
   }
   Post post = (Post) e.nextElement();
   if (post.m_handicap.bonus + post.m_handicap.points < pts) {
    pts = post.m_handicap.bonus + post.m_handicap.points;
    j = i;
   }
   ml = Lib.atoi(post.m_props.getProperty("MORNINGLINE"));
   DO = "";
   Adv20 = "";
   switch (j) {
    case 0:
     if (ml >= 4)
      DO = " DO";
     if (post.m_pointsAdv > 19 && race.m_cnthorses > 6 && race.m_cntnrl < 4
       && race.m_cntnrlML == 0)
      Adv20 = "**";
     break;
    case 1:
     if (ml >= 6)
      DO = " DO";
     break;
    case 2:
     if (ml >= 12)
      DO = " DO";
     break;
    case 3:
     if (ml >= 30)
      DO = " DO";
     break;
    case 4:
     if (ml >= 30)
      DO = " DO";
     break;
    case 5:
     if (ml >= 30)
      DO = " DO";
     break;
   }
   out.println(Lib.pad(post.m_sireTS, 1) + Lib.pad(post.m_sireTS2, 1)
     + Lib.pad(post.m_trainerNamePT.substring(1), 1) + Lib.pad(post.m_ownerTrn, 1)
     + Lib.pad(post.cloth, 4) + Lib.pad(post.m_horseName, 16)
     + Lib.rjust(post.m_handicap.bonus + post.m_handicap.points, 5)
     + Lib.rjust(((j < 6) ? odds[j] : ""), 6)
     + Lib.pad("(" + post.m_morningLine + DO + ")" + Adv20, 15)
     + Lib.pad(post.m_bias, 8));
   i++;
  }
  // Display body language hints
  if (Truline.userProps.getProperty("ShowStats", "N").equals("Y"))
   try {
    out.println("\\par ");
    out
      .println("\\par ====================================== Trainer / Jockey Stats ===============================");
    out.println("\\par ");
    // Display stats for each horse.
    out.println("\\par                    " + Lib.pad("Category", 18)
      + Lib.pad("STS", 6) + Lib.pad("Win%", 6) + Lib.pad("ITM%", 6)
      + Lib.pad("ROI", 7));
    for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
     Post post = (Post) e.nextElement();
     String cat = "";
     int sts = 0;
     int win = 0;
     int itm = 0;
     String roi = "";
     String entry = post.m_props.getProperty("ENTRY", "");
     if (!entry.equals("S")) {
      out.println("\\par " + Lib.pad(post.cloth, 4)
        + Lib.pad(post.m_horseNameP, 18) + " Trainer / Jockey Statistics");
      for (Enumeration e1 = post.m_trainerJockeyStats.elements(); e1
        .hasMoreElements();) {
       TrainerJockeyStats tjs = (TrainerJockeyStats) e1.nextElement();
       cat = tjs.m_props.getProperty("TRAINERCAT1", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN1", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM1", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI1", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT1", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS1", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN1", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM1", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI1", " "), 7));
       }
       cat = tjs.m_props.getProperty("TRAINERCAT2", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN2", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM2", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI2", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT2", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS2", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN2", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM2", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI2", " "), 7));
       }
       cat = tjs.m_props.getProperty("TRAINERCAT3", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN3", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM3", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI3", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT3", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS3", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN3", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM3", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI3", " "), 7));
       }
       cat = tjs.m_props.getProperty("TRAINERCAT4", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN4", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM4", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI4", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT4", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS4", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN4", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM4", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI4", " "), 7));
       }
       cat = tjs.m_props.getProperty("TRAINERCAT5", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN5", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM5", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI5", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT5", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS5", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN5", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM5", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI5", " "), 7));
       }
       cat = tjs.m_props.getProperty("TRAINERCAT6", "N/A");
       win = Lib.atoi(tjs.m_props.getProperty("TRAINERWIN6", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("TRAINERITM6", "0"));
       roi = tjs.m_props.getProperty("TRAINERROI6", "XXX");
       if (!cat.equals("N/A")
         && ((win > 19) || (itm > 50) || (!roi.substring(0, 1).equals("-")) || (roi
           .substring(0, 2).equals("0.0")))) {
        out.println("\\par  "
          + Lib.pad(post.m_props.getProperty("TRAINER", "").toLowerCase(), 17)
          + " " + Lib.pad(tjs.m_props.getProperty("TRAINERCAT6", "N/A"), 18)
          + Lib.pad(tjs.m_props.getProperty("TRAINERSTS6", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERWIN6", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERITM6", " "), 6)
          + Lib.pad(tjs.m_props.getProperty("TRAINERROI6", " "), 7));
       }
       sts = Lib.atoi(tjs.m_props.getProperty("JOCKEYDISTSTS", "0"));
       win = Lib.atoi(tjs.m_props.getProperty("JOCKEYDISTWIN", "0"));
       itm = Lib.atoi(tjs.m_props.getProperty("JOCKEYDISTWIN", "0"))
         + Lib.atoi(tjs.m_props.getProperty("JOCKEYDISTPLC", "0"))
         + Lib.atoi(tjs.m_props.getProperty("JOCKEYDISTSHW", "0"));
       if (sts > 0) {
        win = (sts > 0) ? (win * 100) / sts : 0;
        itm = (sts > 0) ? (itm * 100) / sts : 0;
       }
       out.println("\\par  "
         + Lib.pad(post.m_props.getProperty("JOCKEY", "").toLowerCase(), 17)
         + " " + Lib.pad(tjs.m_props.getProperty("JOCKEYDISTCAT", "N/A"), 18)
         + Lib.pad(tjs.m_props.getProperty("JOCKEYDISTSTS", " "), 6)
         + Lib.pad(win, 6) + Lib.pad(itm, 6)
         + Lib.pad(tjs.m_props.getProperty("JOCKEYDISTROI", ""), 7));
      }
     }
    }
   } catch (Exception e1) {
    Log.print("Exception Writing Statistics: " + e1 + "\n");
   }
  out.println("\\par ");
  out
    .println("\\par ========================================== Race Payoffs =====================================");
  out.println("\\par ");
  if (race.m_resultsPosted.equals("Y")) {
   out.println("\\par            1st  " + Lib.rjust(race.m_cloth1, 3)
     + Lib.rjust(race.m_win1, 8) + Lib.rjust(race.m_place1, 8)
     + Lib.rjust(race.m_show1, 8));
   out.println("\\par ");
   out.println("\\par            2nd  " + Lib.rjust(race.m_cloth2, 3)
     + Lib.rjust(race.m_win2, 8) + Lib.rjust(race.m_place2, 8)
     + Lib.rjust(race.m_show2, 8));
   out.println("\\par ");
   out.println("\\par            3rd  " + Lib.rjust(race.m_cloth3, 3)
     + Lib.rjust(race.m_win3, 8) + Lib.rjust(race.m_place3, 8)
     + Lib.rjust(race.m_show3, 8));
   out.println("\\par ");
   out.println("\\par            EX " + Lib.rjust(race.m_exactaPayoff, 17)
     + "  PICK3 " + Lib.rjust(race.m_pick3Payoff, 11) + "  SFCTA "
     + Lib.rjust(race.m_superPayoff, 11));
   out.println("\\par ");
   out.println("\\par            TRIFECTA "
     + Lib.rjust(race.m_trifectaPayoff, 11) + "  PICK4 "
     + Lib.rjust(race.m_pick4Payoff, 11) + "  DD "
     + Lib.rjust(race.m_doublePayoff, 14));
  } else {
   out
     .println("\\par            1st  ____   ______________   ______________  _______________");
   out.println("\\par ");
   out
     .println("\\par            2nd  ____                    ______________  _______________");
   out.println("\\par ");
   out
     .println("\\par            3rd  ____                                    _______________");
   out.println("\\par ");
   out
     .println("\\par            EX ________________  QU_______________  PICK3_______________");
   out.println("\\par ");
   out
     .println("\\par            TRIFECTA __________  DD ______________  OTHER ______________");
  }
  out
    .println("\\par ==============================================================================================");
  out.println("\\par ");
  line += 14;
  out.println("\\page "); // Next page
  // line = line % pagesize;
  // while(line < pagesize)
  // {
  // out.println("\\par ");
  // line++;
  // }
 } catch (Exception e) {
  Log.print("Exception Writing report: " + e + "\n");
 }
}
/* Print Horse Flow Report */
public void writeHFReport(PrintWriter out, Race race)
{
 DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
 DateFormat dtf = DateFormat.getDateTimeInstance(DateFormat.SHORT,
   DateFormat.SHORT);
 String datestr = dtf.format(new Date());
 int line = 0;
 try {
  out.println("\\par                          " + Truline.title
    + "                " + datestr);
  out.println("\\par [" + Truline.version + "] " + Truline.copyright);
  out
    .println("\\par ======================================+=====================================================");
  out.println("\\par Track "
    + Lib.pad(race.m_track, 3)
    + "    "
    + Lib.datetoa(race.m_raceDate)
    + "    Race#"
    + race.m_raceNo
    + "   Distance "
    + Lib.ftoa(((double) race.m_distance) / Handicap.YdPerF, 1)
    + "F"
    + "  Type "
    + ((race.m_raceType.equals("G1")) ? "G1-Stake I" : (race.m_raceType
      .equals("G2")) ? "G2-Stake II"
      : (race.m_raceType.equals("G3")) ? "G3-Stake III" : (race.m_raceType
        .equals("N")) ? "N-nongraded stake"
        : (race.m_raceType.equals("A")) ? "A-allowance" : (race.m_raceType
          .equals("R")) ? "R-Starter Alw"
          : (race.m_raceType.equals("T")) ? "T-Starter Hcp" : (race.m_raceType
            .equals("C")) ? "C-claiming"
            : (race.m_raceType.equals("S")) ? "S-mdn sp wt" : (race.m_raceType
              .equals("M")) ? "M-mdn claimer" : race.m_raceType));
  NumberFormat fmt = NumberFormat.getCurrencyInstance();
  out.println("\\par "
    + ((race.m_claim != 0) ? ("Claim " + fmt.format(race.m_claim))
      : (race.m_purse != 0) ? ("Purse " + fmt.format(race.m_purse))
        : "                ") + "  Surface "
    + ((race.m_surface.equals("D")) ? "Dirt" : "Turf"));
  String sexAge = race.m_props.getProperty("AGESEX", "");
  out.print("\\par AGE/SEX (" + sexAge + ")");
  switch (sexAge.charAt(0)) {
   case 'A':
    out.print(" 2 year olds");
    break;
   case 'B':
    out.print(" 3 year olds");
    break;
   case 'C':
    out.print(" 4 year olds");
    break;
   case 'D':
    out.print(" 5 year olds");
    break;
   case 'E':
    out.print(" 3 & 4 year olds");
    break;
   case 'F':
    out.print(" 4 & 5 year olds");
    break;
   case 'G':
    out.print(" 3, 4, and 5 year olds");
    break;
   case 'H':
    out.print(" all ages");
    break;
  }
  switch (sexAge.charAt(1)) {
   case 'O':
    out.print(", That age Only");
    break;
   case 'U':
    out.print(", That age and Up");
    break;
  }
  switch (sexAge.charAt(2)) {
   case 'N':
    out.print(", No Sex Restrictions");
    break;
   case 'M':
    out.print(", Mares and Fillies");
    break;
   case 'C':
    out.print(", Colts and/or Geldings");
    break;
   case 'F':
    out.print(", Fillies Only");
    break;
  }
  out.println();
  if (Truline.userProps.getProperty("Experimental", "N").equals("Y")) {
   int cnt = 0;
   while (cnt <= race.cntRaceFlows) {
    out.println("\\par " + race.raceFlows[cnt]);
    cnt++;
   }
  }
  out
    .println("\\par =============================================================================================");
  out
    .println("\\par     #  Horse               FLOW CONDITIONS      TL#  ML   FP  ODDS");
  out.println("\\par ");
  line += 9;
  // Display each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String entry = post.m_props.getProperty("ENTRY", "");
   if (entry.equals("S"))
    continue;
   if (post.m_handicap == null || post.m_horseName == null)
    continue; // position is empty
   if (post.cntHorseFlows < 0)
    continue; // No flows for this horse
   String finishPos = post.m_finishPos;
   if (finishPos.equals("1") || finishPos.equals("2") || finishPos.equals("3")
     || finishPos.equals("4"))
    finishPosPrt = finishPos;
   else
    finishPosPrt = "";
   out.println("\\par " + Lib.pad(post.m_sireTS, 1)
     + Lib.pad(post.m_sireTS2, 1) + Lib.pad(post.m_ownerTrn, 1)
     + Lib.pad(post.cloth, 4) + Lib.pad(post.m_horseNameP, 20)
     + Lib.pad(post.horseFlows[0], 16)
     + Lib.rjust(post.m_handicap.bonusRank, 4)
     + Lib.rjust(post.m_morningLine, 5) + Lib.rjust(finishPosPrt, 3)
     + Lib.rjust(post.m_odds, 6));
   line++;
   String lastFlow = post.horseFlows[0];
   int cnt = 1;
   while (cnt <= post.cntHorseFlows) {
    if (!lastFlow.equals(post.horseFlows[cnt])) {
     out.println("\\par                            "
       + Lib.pad(post.horseFlows[cnt], 20)
       + Lib.rjust(post.m_handicap.bonusRank, 4)
       + Lib.rjust(post.m_morningLine, 5) + Lib.rjust(finishPosPrt, 3)
       + Lib.rjust(post.m_odds, 6));
     line++;
    }
    lastFlow = post.horseFlows[cnt];
    cnt++;
   }
  }
  out
    .println("\\par ==============================================================================================");
  out.println("\\par ");
  line += 14;
  out.println("\\page "); // Next page
  // line = line % pagesize;
  // while(line < pagesize)
  // {
  // out.println("\\par ");
  // line++;
  // }
 } catch (Exception e) {
  Log.print("Exception Writing report: " + e + "\n");
 }
}
}
