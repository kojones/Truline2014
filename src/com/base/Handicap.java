package com.base;
/*
 Problem:  11/9

 gpx1106 race 2, horse 2

 >Yes, in doing the Energy Numbers, it seems to be giving the 60% bonus to
 >races, and 40% to workouts, as appropriate. In this same second race,
 >all Energy Numbers were right except for YAMIPA. The three efforts that
 >should have been used are: 10/31 workout, 9/26 race, and 9/22 workout.
 >Instead, for a reason I can't explain, it ignored the 10/31 workout, and
 >used (1) the 9/26 race, (2) the 9/22 workout, and (3) a 9/13 workout.
 >This lowered the Energy Number substantially.
 =============================================================================
 */
/*
 *      The handicaping logic for truline.
 *
 *      There should be one Handicap Object created for each horse in a race (each post).
 *
 *SOME BACKGROUND INFORMATION:
 *
 *      NOTE: A furlong is 220 yards.  A 1/5th of a second
 *                is one horse length.  A variant point is considered
 *                to be worth 1/5th of a second.
 Anecdotally, as mentioned, a horse stretched out is 11-12', and that's
 where a fifth of a second cames from. In the 70's, some Ivy League
 mathematicians began arguing that the correct measurement was from the
 point the rear feet pushed off to the point the front feet touched
 ground. This got a little hairy when one compared a small filly (22' in
 this case) vs the great SECRETARIAT (33'). Another group argued that it
 should be the horse while standing, measured from point of tail to nose
 This is usually about 7-8', which would mean 7-8 lengtha per second. To
 tell you the truth, I don't know which is right. Thoroughbred racing
 started in the days of George Washington. They said one-fifth was a
 length. This was the case for 200 years of racing. So that's seemed good
 enough to me. (Plus it is the concept that the Daily Racing Form is
 based on.) This is one place where you can't fight City Hall.
 *
 *      REPRESENTITIVE RACE:
 In order to evaluate a horses chances of winning a race we must find
 some information about the horse on which to base our evaluation.
 For this we look at a horse's past performance.  Among the horses
 previous races we look for one that represents the horses best effort.
 This is the Representitive race.  We then add adjustments to the
 race's running line and speed ratings to normalize it for variations
 in track and other conditions.
 *
 *      TIME, VELOCITY, AND SPEED:
 Basically, we will be talking time and speed rating. The "running line"
 - which is in fractions for the race segments, then final time, is in
 seconds and fractions thereof (fifths of a second as to segments. 6F and
 longer, final time is in minutes, seconds, and fifths.

 But to get "velocity per second", as used in TRULINE, we convert to
 tenths. So,
 22.3       45.4             111:4           becomes
 22.6       45.8             111.8

 The speed figure is a numerical value, rarely below 50 - usually in a
 range of 65-105, that is a relationship to average winning times (par
 times) for the distance. A value of 100 would be the average or par. If
 exceeding 100, it would be a rare and special race, and they'd have to
 re-do their figures. The speed rating is accompanied (column to its
 right) by a value representing the Daily Track Variant, hopefully
 representing the degree that weather or maintenance has changed the
 surface off of "true fast". "0" would be perfectly true.

 We will have a Table for comparitive weight. Today's weight will be
 matched against the weight per rep race. If not identical there will be
 a +/- adjustment, dependent on whether weight is increasing today or
 decreasing. The adjustment number will be added to the Variant. Today's
 race will also be compared based on the Track Class Table. If it is not
 the same, there will be a +/- adjustment, dependent on whether the horse
 is moving up or down in Track Class. This will be added to the Variant.
 Today's race in terms of Purse will be compared to the Purse Class
 Table. Again, a +/- figure derives and is applied to the Variant.

 Once all these adjustments are made, and we have a total figure,
 whatever the figure it represents fifths of a second. Converting to
 tenths, 20% of the total figure is added (or subtracted) from the time
 at the first call (segment); 50% of the total modifies the second
 call/segment, and the total figure is applied to the final time.

 Now, where one can get easily confused, there is a polarity reversal
 involved with this total. The running line says the horse ran the race
 in 1:11:3. The total of the Variant and these other adjustments is +20.
 This means that at least in theory, the track surface and weight and
 Class slowed this horse up by four full seconds. On a "true fast" track,
 the horse should run four seconds faster. So we SUBTRACT the figure, and
 give the horse a "best race" potential time of 1:07:3.

 DRF Running Line               22.4    45.3      111.3
 Modified running line          21.6    43.6      107.6

 BUT, when dealing with AS and RE, we are using the SPEED RATING. For
 this, we must ADD the total value. So, if the DRF speed rating was 77,
 we would add +20 and change this to 97. In turn, a 97 speed rating would
 be consistent to a final time of 1:07:3.

 DISTANCE:
 Distance - 220 yards, yes. For our purposes, sprints are 5F and 5.5F
 (mostly for young horses, two-year-olds, and early in the year for
 three-year-olds. A few for broken down older horses. No major Stakes  at
 these distances. The largest number of races at sprint distance is 6F.
 Some tracks have 6.5F races as well, others 7F.

 Technically, there are a few races at a few tracks at 7.5F. TRULINE
 should ignore these. Short routes are divided between 8F and 8.5F.

 Most of the big Stakes races January through April are at 9F. 9F and up
 are regarded as "classic" distances. The first week May the Kentucky
 Derby is run 1t 10F. Then the Preakness in Maryland drops back slightly
 to 9.5F. The Belmont, third leg of the Triple Crown in June is at 12F.
 That's the longest major Stakes.

 In sprints, there are three running line "calls", the quarter, the half,
 and final time. This gives us the 23.3, 45.4, and 111.1, the final time.
 This is FS, SS, and FT. Subtracting FS from SS gets us TT; and
 substracting SS from FT gets us CS. EXCEPT, that on the Tote Board, and
 sometimes also in the DRF, there is a stretch call. That's when you have
 four calls instead of three. It creates a measurement of the final
 eighth or sixteenth of a mile. If you are watching races on TV, this is
 what the timer up in the corner shows, HOWEVER, this is NOT GIVEN by
 many tracks, and very erratically by the Daily Racing Form, so TRULINE
 doesn't use this break-down, The software, like us, would never know
 where to find it.

 In short routes, 8F to 9F, there are also three. The first is at the
 half mile pole, tthe second is at the six furlong poll, and then the
 final time. So we have 45.4 as the first call, 111.4 as the second call,
 and then the final time.  You are right about beaten lengths.

 */
import com.base.Handicap;
import com.base.Lib;
import com.base.Log;
import com.base.Performance;
import com.base.Post;
import com.base.Race;
import com.base.Workout;

import java.util.*;
import java.text.*;

import com.mains.Truline;
public class Handicap
{
 // Distances in yards
 public static final int F5_0      = 1100;                    // 5.0 Furlongs
 public static final int F5_5      = 1210;                    // 5.5 Furlongs
 public static final int F6_0      = 1320;                    // 6.0 Furlongs
 public static final int F6_5      = 1430;                    // 6.5 Furlongs
 public static final int F7_0      = 1540;                    // 7.0 Furlongs
 public static final int F7_5      = 1650;                    // 7.5 Furlongs
 public static final int F8_0      = 1760;                    // 8.0 Furlongs
 public static final int F8_1      = 1800;                    // 8.1 Furlongs
 public static final int F8_3      = 1830;                    // 8.3 Furlongs
 public static final int F8_5      = 1870;                    // 8.5 Furlongs
 public static final int F9_0      = 1980;                    // 9.0 Furlongs
 public static final int F9_5      = 2090;                    // 9.5 Furlongs
 public static final int F10       = 2200;                    // 10 Furlongs
 public static final int F11       = 2420;                    // 11 Furlongs
 public static final int F12       = 2840;                    // 12 Furlongs
 public static final int YdPerF    = 220;
 // Adjusted values:
 public static final int FS        = 0;                       // First Call
                                                               // (Front Speed)
 // For sprints this will be the time to the quarter-mile pole.
 // This is a guage of early speed, particularly to identify a
 // sole front runner.
 // In routes [8F to 10F] the first call is the half-mile pole.
 // Races longer than 10F usually will have the 6F pole as first call.
 public static final int SS        = 1;                       // Second Call
                                                               // (Sustained
                                                               // Speed)
 // Sprints, this is the half mile point.
 // In Routes it is at 6F pole. Routes over 1-13/16 mile
 // the second call may be the one mile pole.
 public static final int FT        = 2;                       // Final time
 public static final int TT        = 3;                       // Turn time (ss
                                                               // - fs)
 public static final int CS        = 4;                       // Closing time
                                                               // (ft - ss)
 // For races at 6,7,8,9,10F this wil be the final quarter.
 // For races at 5.5, 6.5, 7.5, 8.5, 9.5, and 10.5F the
 // last 1/16th.
 public static final int AS        = 5;                       // Adjusted speed
                                                               // = DRF Speed
                                                               // figure + DRF
                                                               // Variant as
                                                               // modified.
 public static final int RE        = 6;                       // Recency speed
                                                               // (within 28
                                                               // days).
 public static final int QP        = 7;                       // Quirin points
 public static final int EN        = 8;                       // Energy
                                                               // Quotient
 public static final int EPS       = 9;                        // Total Earnings
                                                               // per start
 public static String[]  names     = { "FS", "SS", "FT", "TT", "CS", "AS",
   "RE", "QP", "EN", "EPS"        };
 public double[]         value     = new double[names.length]; // parameter
                                                               // values
 public int[]            rank      = new int[names.length];   // Handicap rank
 public int              points    = 0;
 public int              bonus     = 0;
 public int              bonusRank = 999;
 public boolean          extraFlg;                             // Extra energy
                                                               // flag
 public Performance      m_repRace = null;                     // Representitive
                                                               // race
 public Performance      m_recency = null;                     // Recency race
 /**
  * Constructor
  */
 public Handicap() {
 }
 /**
  * Main driver for truline logic
  *
  * This is a static routine. It will create one instance of this class for each
  * horse in the race, each attached to the horse's Post instance.
  */
 public static void compute(Race race)
 {
  if (Log.isDebug(Log.MINIMUM)) {
   Log.print("\n================ Handicap for RACE #" + race.m_raceNo
     + "===================\n");
  }
  Truline.println("Handicap for Race #" + race.m_raceNo);
  // Get the track purse class for this race.
  // This is obtained from the truline.pc file
  // Which has fields TRACK, CLASS.
  Properties classData = Truline.pc.get("TRACK", race.m_track);
  if (classData != null) {
   String trackClassStr = classData.getProperty("CLASS");
   if (trackClassStr != null)
    race.m_trackClass = Lib.atoi(trackClassStr);
  } else
   Log.print("WARNING: Could not find track " + race.m_track
     + " in truline.tc\n");
  // Get the race class for this race.
  // This is obtained from truline.rc file
  // Which has fields RACETYPE, PURSE, RACECLASS.
  race.m_raceType = race.m_props.getProperty("RACETYPE", "");
  race.m_purse = Lib.atoi(race.m_props.getProperty("PURSE", ""));
  race.m_claim = Lib.atoi(race.m_props.getProperty("CLAIMPRICE", ""));
  // race.m_purseClass = getPurseClass(race.m_raceType, race.m_purse);
  race.m_age = race.m_props.getProperty("AGESEX", "");
  // Maiden Claiming races are not bettable
  // if (race.m_raceType.equals("M"))
  //  race.m_bettable1 = "N";
  // Get the rest of the Race related data
  race.m_distance = Lib.atoi(race.m_props.getProperty("DISTANCE"));
  if (race.m_distance < 0)
   race.m_distance = -race.m_distance;
  race.m_surface = race.m_props.getProperty("SURFACE", "").toUpperCase();
  race.m_surfaceLC = race.m_props.getProperty("SURFACE", "");
  race.m_allweather = race.m_props.getProperty("ALLWEATHER", "").toUpperCase();
  if (race.m_surface.equals("X"))
   race.m_surface = "T";
  // 2-year old races are not bettable
  String sexAge = race.m_props.getProperty("AGESEX", " ");
  if (sexAge.charAt(0) == 'A')
   race.m_bettable2 = "N";
  // Races less than 6 furlongs or at odd distances are low-probability
  if (race.m_distance < 1320
    || (race.m_distance > 1760 && race.m_distance < 1870))
   race.m_bettable2 = "N";
  // Races at the lowest purses or claiming are also low probability
  if (race.m_trackClass < 2) // Better tracks
  {
   if ((race.m_claim > 0 && race.m_claim < 10000)
     || (race.m_claim == 0 && race.m_purse < 10000))
    race.m_bettable2 = "N";
  } else // lesser tracks
  {
   if ((race.m_claim > 0 && race.m_claim < 5000)
     || (race.m_claim == 0 && race.m_purse < 5000))
    race.m_bettable2 = "N";
  }
  int recencyDays = Lib
    .atoi(Truline.userProps.getProperty("RecencyDays", "28"));
  Calendar cal = Calendar.getInstance();
  cal.setTime(race.m_raceDate);
  cal.add(Calendar.DAY_OF_YEAR, -recencyDays);
  race.m_recencyDate = cal.getTime();
  race.m_maxvariant = Lib.atoi(Truline.userProps
    .getProperty("MaxVariant", "25"));
  race.m_useMaiden = Truline.userProps.getProperty("UseMaiden", "Y")
    .startsWith("Y");
  if (race.m_raceType.equals("M") || race.m_raceType.equals("S"))
   race.m_useMaiden = true;
  race.m_maxdays = Lib.atoi(Truline.userProps.getProperty("MaxDays", "365"));
  cal = Calendar.getInstance();
  cal.setTime(race.m_raceDate);
  cal.add(Calendar.DATE, -race.m_maxdays);
  race.m_cutoff = cal.getTime();
  if (Log.isDebug(Log.TRACE)) {
   Log.print("  Track=" + race.m_track + ", Track class is "
     + race.m_trackClass + "\n");
   Log.print("  RaceDate=" + Lib.datetoa(race.m_raceDate) + "  Race #"
     + race.m_raceNo + "\n");
   Log.print("  Purse=" + race.m_purse + ", race type=" + race.m_raceType
     + ", Purse class is " + race.m_purseClass + "\n");
   Log.print("  Surface=" + race.m_surface + ", Distance="
     + toF(race.m_distance) + "\n");
   Log.print("  Maxdays days=" + race.m_maxdays + " which is "
     + Lib.datetoa(race.m_cutoff) + "\n");
   Log.print("  Recency days=" + recencyDays + " which is "
     + Lib.datetoa(race.m_recencyDate) + "\n");
   Log.print("  Variant cutoff is set at " + race.m_maxvariant + "\n");
   if (race.m_useMaiden)
    Log.print("  We are using maiden races.\n");
  }
  /*
   * Following routine is no longer necessary since BRIS file contains program
   * number // Determine the saddle cloth numbers on each horse. String[] part =
   * {"", "A", "B", "C", "D", "E", "F"}; int[] mate = {0, 0, 0}; int[] num = {0,
   * 0, 0}; int partnbr = 0; int field = 1; for(Enumeration e =
   * race.m_posts.elements(); e.hasMoreElements();) { Post post =
   * (Post)e.nextElement(); String entry = post.m_props.getProperty("ENTRY","");
   * if (entry.equals("A")) { if (mate[0] == 0) mate[0] = field++; post.cloth =
   * mate[0] + part[num[0]++]; } else if (entry.equals("B")) { if (mate[1] == 0)
   * mate[1] = field++; post.cloth = mate[1] + part[num[1]++]; } else if
   * (entry.equals("C")) { if (mate[2] == 0) mate[2] = field++; post.cloth =
   * mate[2] + part[num[2]++]; } else post.cloth = ""+(field++); }
   */
  // For each horse in the race, determine the representitive race
  // and compute its points.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   // If results posted and horse did not finish, it was scratched
   if (Truline.userProps.getProperty("PostResults", "N").equals("Y")
     && race.m_resultsPosted.equals("Y") && post.m_finishPos.equals(""))
    post.m_props.setProperty("ENTRY", "S");
   String entry = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || entry.equals("S")) {
    // Log.errPrint("Scratch RACE #"+race.m_raceNo+" post="+post.m_postPosition+"  Cloth="+post.cloth+"\n");
    continue; // position is empty or scratched
   }
   post.m_handicap = new Handicap();
   for (int i = 0; i < post.m_handicap.value.length; i++) {
    post.m_handicap.value[i] = 0;
    post.m_handicap.rank[i] = 999;
   }
   post.m_handicap.evaluateHorse(race, post);
  }
  rankHorses(race);
  assignPoints(race);
  race.ranking = assignBonusPoints(race);
  if (Truline.userProps.getProperty("TL2014", "N").equals("Yes")) {
   identifyFlowBets(race);
   identifyTrnFlowBets(race);
   identifyRaceFlowBets(race);
  }
 }
 /**
  * For a given horse in a race, determine its represntitive race and then
  * compute its points. The points will establish the horse's ranking.
  */
 public void evaluateHorse(Race race, Post post)
 {
  if (Log.isDebug(Log.TRACE)) {
   Log.print("\nRace #" + race.m_raceNo + ", Post Position "
     + post.m_postPosition + " Horse: " + post.m_horseName + "\n");
   Log.print("                Weight: " + post.m_weight + "\n");
  }
  // Select representitive race canidates:
  Vector canidates = selectCandidates(race, post);
  if (canidates == null)
   return;
  // Determine which of the candidates is the best rep race.
  determineRepRace(race, post, canidates);
  if (m_repRace != null) {
   value[FS] = m_repRace.fs;
   value[SS] = m_repRace.ss;
   value[FT] = m_repRace.ft;
   value[TT] = m_repRace.tt;
   value[CS] = m_repRace.cs;
  }
  // Adjusted speed = DRF Speed figure + DRF Variant as modified.
  if (m_repRace != null)
   value[AS] = m_repRace.as; // for Representitive race
  if (m_recency != null)
   value[RE] = m_recency.as; // for Recency race
  // compute points
  value[QP] = quirinPoints(race, post);
  // compute EQ (Energy Quotient)
  value[EN] = computeEQ(race, post);
  // compute EPS (Earnings per start)
  value[EPS] = computeEPS(race, post);
 }
 /**
  * Select representitive race candidates: Consider only if:
  *
  * 1) candidate race date is within 120 days (value 120 taken from the
  * BVmaxdays= in user file) 2) Both todays and candidates race are turf or both
  * are dirt. 3) candidate races's variant (DTV) is +20 or less (value +20 taken
  * from BVmaxvariant= in user file) 4) The distance of the canidate race must
  * match todays race according to the following table: 5F for 5F 5.5F for 5.5F
  * 6F or 6.5F for 6F or 6.5F 7F for 7F 7.5F for 7.5F 8F or 8.5F for 8F or 8.5F
  * 9F for 9F 9.5F for 9.5F 10F for 10F 11F for 11F 12F for 12F treat this as a
  * 9F and find a rep race among those.
  *
  *
  * 5) Only use Maiden races if the flag "Maiden in Win" = Y (flag from
  * BVuseMaiden=Y in user file) unless todays race is a maiden race. 6) Stakes
  * G1 - the only races that can be rep races are a finish of first, second, or
  * third in a G2 race or a Win in a G3 or other such race with a Purse of
  * $250,000 or higher. At G1 level, horses successful in Allowance, Claiming
  * and even Maiden Special Weight are not of a G1 quality. Making that jump
  * successfully would be a Cinderella Story. It would be thrilling, but it
  * wouldn't have MY money on it.
  *
  * G2 - A horse must have been first, second or third in a G2 or G3 race (or
  * naturally G1) or other race of $250,000 value or higher.
  *
  * G3 - Must have been first, second, or third in a Graded Stake, or a winner
  * of more than two races in a combination of Allowance and Maiden Special
  * Weight prior races.
  *
  * 7) Position data (lengths) and time data must be available. 8) Do not use
  * race if first two letters of track abbr match with something in 'EX' table
  * (exclude table) in the user file.
  */
 private Vector selectCandidates(Race race, Post post)
 {
  Vector candidates = new Vector();
  if (Log.isDebug(Log.TRACE)) {
   Log.print("  Selecting Canidates for Rep Race:\n");
  }
  for (Enumeration e = post.m_performances.elements(); e.hasMoreElements();) {
   Performance p = (Performance) e.nextElement();
   // Check the exclude list
   boolean found = false;
   for (Enumeration exList = Truline.ex.elements(); exList.hasMoreElements();) {
    Properties prop = (Properties) exList.nextElement();
    String exTrack = prop.getProperty("TRACK", "");
    if (exTrack.length() == 2)
     exTrack += "X";
    String track = p.ppTrack;
    if (track.length() == 2)
     track += "X";
    if (track.equals(exTrack)) {
     found = true;
     break;
    }
   }
   if (found) {
    if (Log.isDebug(Log.TRACE)) {
     Log.print("  Canidate: Track: " + p.ppTrack + " "
       + Lib.datetoa(p.ppRaceDate) + ", Race#" + p.ppRaceNo + "\n");
     Log.print("            Excluded track--skipped\n");
    }
    p.isExcluded = true;
    continue;
   }
   p.isExcluded = false;
   String pSurface = p.m_props.getProperty("PPSURFACE", "").toUpperCase();
   double distanceAdj = distanceAdjustments(race.m_age, race.m_distance, p);
   p.variant = Lib.atoi(p.m_props.getProperty("VARIENT", "100"));
   p.drfSpeed = Lib.atof(p.m_props.getProperty("DRFSPEED"));
   String pracetype = p.m_props.getProperty("PPRACETYPE", "");
   int purse = Lib.atoi(p.m_props.getProperty("PURSE"));
   int claim = Lib.atoi(p.m_props.getProperty("PPCLAIMPRICE"));
   String ab = p.m_props.getProperty("ABOUT");
   boolean about = (ab != null && ab.equals("Y"));
   String chute = p.m_props.getProperty("CHUTE", "");
   // Make sure there is running line info
   double pos0 = Lib.atof(p.m_props.getProperty("POSITION1", "")); // Position
                                                                   // at
                                                                   // Starting
                                                                   // Gate
   double pos1 = Lib.atof(p.m_props.getProperty("POSITION2", "")); // Position
                                                                   // at First
                                                                   // Call
   double pos2 = Lib.atof(p.m_props.getProperty("POSITION3", "")); // Position
                                                                   // at Second
                                                                   // Call
   double pos3 = Lib.atof(p.m_props.getProperty("POSITION5", "")); // Position
                                                                   // at Third
                                                                   // Call
   double pos4 = Lib.atof(p.m_props.getProperty("POSITION6", "")); // Position
                                                                   // at Finish
   double f1 = Lib.atof(p.m_props.getProperty("FRACTION1")); // time of the
                                                             // leader
   double len1 = Lib.atof(p.m_props.getProperty("LENGTHS1")); // lengths behind
                                                              // leader
   double f2 = Lib.atof(p.m_props.getProperty("FRACTION2")); // time of the
                                                             // leader
   double len2 = Lib.atof(p.m_props.getProperty("LENGTHS2")); // lengths behind
                                                              // leader
   double f3 = Lib.atof(p.m_props.getProperty("FRACTION3")); // time of the
                                                             // leader
   double len3 = Lib.atof(p.m_props.getProperty("LENGTHS3")); // lengths behind
                                                              // leader
   double f4 = Lib.atof(p.m_props.getProperty("FINALTIME")); // time of the
                                                             // leader at finish
   double len4 = Lib.atof(p.m_props.getProperty("LENGTHS4")); // lengths behind
                                                              // leader at
                                                              // finish
   if (pos1 == 1)
    len1 = 0;
   if (pos2 == 1)
    len2 = 0;
   if (pos3 == 1)
    len3 = 0;
   if (pos4 == 1)
    len4 = 0;
   p.isRoute = isRoute(p);
   boolean stakesRace = false;
   boolean pHighStakesRace = false;
   /*
    * Check the statkes rep race option flag - if not set then old rules apply
    */
   if (Truline.userProps.getProperty("RRStakesCheck", "A").equals("N"))
     pHighStakesRace = true;
   else if (Truline.userProps.getProperty("RRStakesCheck", "A").equals("T") && race.m_surface.equals("D"))
    pHighStakesRace = true;
   else if (Truline.userProps.getProperty("RRStakesCheck", "A").equals("D") && race.m_surface.equals("T"))
    pHighStakesRace = true;

   /*
    * For 2-year olds, accept any otherwise qualifying race as a Candidate if
    * today is Turf
    */
   if (race.m_age.startsWith("A")
     && (race.m_raceType.equals("G1") || race.m_raceType.equals("G2"))
     && p.isRoute)
    pHighStakesRace = true;
   if (race.m_raceType.equals("G1") || race.m_raceType.equals("G2")) {
    stakesRace = true;
    if (pracetype.equals("G1")) {
     pHighStakesRace = true;
    } else if (pracetype.equals("G2")) {
     if (pos4 >= 1 && pos4 <= 3) // finished in the top three positions
      pHighStakesRace = true;
    } else if (pracetype.equals("G3")) {
     if (pos4 == 1) // finished first
      pHighStakesRace = true;
    } else if (purse >= 250000) {
     if (pos4 >= 1 && pos4 <= 3) // finished in the top three positions
      pHighStakesRace = true;
    }
   } else if (race.m_raceType.equals("G3") && (purse >= 250000 || purse == 0)) {
    stakesRace = true;
    if (pracetype.equals("G1")) {
     pHighStakesRace = true;
    } else if (pracetype.equals("G2") || pracetype.equals("G3")) {
     if (pos4 >= 1 && pos4 <= 3) // finished in the top three positions
      pHighStakesRace = true;
    } else if ((pracetype.equals("A") || pracetype.equals("S"))
      && purse > 50000) {
     if (pos4 == 1) // finished first
      pHighStakesRace = true;
    }
   }
   if (Log.isDebug(Log.TRACE)) {
    Log.print(" Candidate: Track: " + p.ppTrack + " "
      + Lib.datetoa(p.ppRaceDate) + ", Race#" + p.ppRaceNo + "\n");
    Log.print("            " + ((p.isRoute) ? "ROUTE " : "SPRINT ")
      + " - Race Classification=" + p.m_props.getProperty("RACECLASSIFICATION")
      + "\n");
    Log.print("            " + Lib.dateDiff(p.ppRaceDate, race.m_raceDate)
      + " days back, "
      + (p.ppRaceDate.after(race.m_cutoff) ? "Within " : "Outside ")
      + race.m_maxdays + " days\n");
    Log.print("            Surface=" + pSurface + ", "
      + ((race.m_surface.equals(pSurface)) ? "SAME" : "DIFFERENT")
      + ((!chute.equals("")) ? (" Chute=" + chute) : "") + "\n");
    Log.print("            Distance="
      + toF(p.ppDistance)
      + ", "
      + ((p.ppDistance > F10) ? " Greater than 10F - cannot use"
        : (distanceAdj != 999) ? "SIMILAR" : "DIFFERENT")
      + ((about) ? " (ABOUT)" : "") + "\n");
    Log.print("            variant="
      + ((p.variant == 100) ? "No Variant Given" : p.variant + ", "
        + ((p.variant <= race.m_maxvariant) ? "OK" : "Beyond cutoff"))
      + ", DRF Speed rating=" + p.drfSpeed
      + ((p.drfSpeed == 0) ? " Cannot use" : "") + "\n");
    Log
      .print("            "
        + ((!pracetype.equals("M") && !pracetype.equals("S")) ? "Not Maiden Race, OK"
          : (race.m_useMaiden) ? "Using Maiden Race"
            : "Maiden Race, cannot use") + "\n");
    Log.print("            Race type=" + pracetype + ", purse=" + purse
      + ", claim=" + claim + "\n");
    Log.print("            "
      + ((!stakesRace) ? "Todays race Not Stakes Race - OK"
        : (pHighStakesRace) ? "Qualifies for high Stakes Race - OK"
          : "Does not qualify for high Stakes Race - Cannot use") + "\n");
    Log.print("            Available Data: Gate Position=" + pos0 + "\n");
    Log.print("                    Call#1: POS=" + pos1 + ", LEN=" + len1
      + " Time=" + f1 + "\n");
    Log.print("                    Call#2: POS=" + pos2 + ", LEN=" + len2
      + " Time=" + f2 + "\n");
    Log.print("                    Call#3: POS=" + pos3 + ", LEN=" + len3
      + " Time=" + f3 + "\n");
    Log.print("                    Finish: POS=" + pos4 + ", LEN=" + len4
      + " Time=" + f4 + "\n");
    String frac = "";
    for (int i = 2; i < 13; i++) {
     String f = p.m_props.getProperty("FRACTION" + i + "F");
     if (f != null) {
      frac += ((!frac.equals("")) ? ", " : "") + i + "F=" + f;
     }
    }
    Log.print("                    " + frac + "\n");
    if (pos2 == 0 || f2 == 0)
     Log.print("                     Not usable, no running line\n");
   }
   if (p.ppRaceDate.after(race.m_cutoff) // within 365 days
     && ((Truline.userProps.getProperty("IgnoreSurface", "N").equals("Y")) || race.m_surface
       .equals(pSurface)) // same surface
     && p.drfSpeed > 0 // Must have DRFSpeed rating
     && p.variant <= race.m_maxvariant // Less than +25 variant (DVT cutoff)
     && (p.ppDistance <= F10 || p.ppDistance == race.m_distance) // Not over 10F
                                                                 // or same
                                                                 // distance as
                                                                 // today
     && distanceAdj != 999 // similar distance
     && (race.m_useMaiden || (!pracetype.equals("M") && !pracetype.equals("S"))) // use
                                                                                 // maiden
     && (!stakesRace || pHighStakesRace) // stakes match
     && (pos2 != 0 && f2 != 0)) {
    candidates.addElement(p);
    if (Log.isDebug(Log.TRACE))
     Log.print("            Acceptable candidate\n\n");
   } else {
    if (Log.isDebug(Log.TRACE))
     Log.print("            Candidate not used\n\n");
   }
  }
  return candidates;
 }
 /**
  * For each horse in race, determine what would make a good representitive race
  * and a Recency race from among the candidates. --------------------
  * Basically, the representative race refers to the running line that gives the
  * fractional times and final time of the race. We want to use the horse's best
  * effort.
  *
  * In a separate location as to the horse's information, we are given a speed
  * rating (a value hinged to a historical average for the
  * distance/class/age/sex. And we are given a Daily Track Variant, which is to
  * identify that particular day's track surface characteristics vs what would
  * be described as a "true fast" track.
  *
  * Let me give as an example two races in a horse's history:
  *
  *
  * Oct, 12 6F 22.1 45.1 109:4 91 12 Nov. 18 6F 22.4 45.3 110:4 86 20
  *
  * Based on final time, the first race would seemingly be the best. This is not
  * the case. The Daily Track Variant would alter these two running lines to:
  *
  * Oct. 12 6f 21.7 44.0 107.4 Nov. 18 6f 22.0 43.6 106.6
  *
  * (Note: the DRF figures are in minutes and fifths of a second. Our revised
  * running line is in tenths.) At any rate, the second race was the best race.
  * Similarly, if simply adding speed rating and Daily Track Variant (AS), the
  * second race woule be the best race.
  *
  * The program is also supposed to include in this adjustment factors for
  * changes in Track Class, Purse Class, weight and distance.
  *
  * As a totally separate computation, Recency is a measurement of the horse's
  * best effort in the last 28 days. This is the highest total of speed rating
  * and Daily Track Variant in the last 28 days. When this value is lower that
  * AS, then it is telling the User that the rep race came from an earlier time
  * period, and may be an aspect of "Back Class".
  */
 public void determineRepRace(Race race, Post post, Vector canidates)
 {
  double adj;
  if (Log.isDebug(Log.TRACE))
   Log.print("  Select Best Rep Race from Qualified Canidates\n");
  // look at each canidate and determine the best match
  for (Enumeration c = canidates.elements(); c.hasMoreElements();) {
   Performance p = (Performance) c.nextElement();
   // Compute speed
   // compute speed rating = BRIS speed rating for this race
   // + adjusted variant
   p.as = p.drfSpeed;
   if (Log.isDebug(Log.TRACE)) {
    Log.print("    Canidate: Track: " + p.ppTrack + " "
      + Lib.datetoa(p.ppRaceDate) + ", Race#" + p.ppRaceNo + "\n");
    Log.print("              DRF speed rating  =" + p.drfSpeed + "\n");
    Log.print("              DRF Track Variant =" + p.variant + "\n");
   }
   // Weight adjustments
   adj = weightAdjustments(post.m_weight, p);
   p.variant += adj;
   // Distance adjustments
   adj = 0;
   if (race.m_distance == F5_0 && p.ppDistance == F6_0)
    adj = 5;
   if (race.m_distance == F5_5 && p.ppDistance == F6_0)
    adj = 7;
   if (race.m_distance == F6_0 && p.ppDistance == F5_5)
    adj = -7;
   if (Log.isDebug(Log.TRACE) && adj != 0) {
    Log.print("              distance adjustment=" + adj + "  ("
      + toF(p.ppDistance) + " to " + toF(race.m_distance) + ")\n");
   }
   p.variant += adj;
   // Class adjustments
   // Track Purse Class Adjustments:
   // lookup rep race track in the class table.
   // Every track has a number #1 thru #8. ONE POINT should be added
   // variant for each level of Class drop, ONE POINT subtracted from
   // each level of upward move.
   if (race.m_trackClass > 0) {
    Properties trackData = Truline.pc.get("TRACK", p.ppTrack);
    if (trackData != null) {
     String trackClassStr = trackData.getProperty("CLASS");
     if (trackClassStr != null) {
      adj = (race.m_trackClass - Lib.atof(trackClassStr));
      if (Log.isDebug(Log.TRACE))
       Log.print("              track class " + trackClassStr + " adjustment="
         + adj + "\n");
      p.variant += adj;
     }
    } else {
     if (Log.isDebug(Log.TRACE))
      Log
        .print("              Track purse class not found in purse class table\n");
    }
   }
   /*
    *  *** The race type / purse class change is no longer used // Purse Class
    * Adjustments: // lookup rep race type and purse in class table. // At lower
    * Class levels, we adjusted a length (a point) // for each $1000 of Purse.
    * At Allowance level, We adjusted // one length (point) for each $3000 of
    * Purse. // The amount of adjustment for each level is set in the // race
    * class table. if (race.m_purseClass > 0) { String raceType =
    * p.m_props.getProperty("PPRACETYPE",""); int purse =
    * Lib.atoi(p.m_props.getProperty("PURSE","")); if
    * (race.m_raceType.equals(raceType) && purse == 0) { if
    * (Log.isDebug(Log.TRACE))
    * Log.print("              purse is 0, assume same class (Purse="
    * +purse+", race type="+raceType+")\n"); } else { double cl =
    * getPurseClass(raceType, purse); if (cl > 0) { adj = cl -
    * race.m_purseClass; if (Log.isDebug(Log.TRACE))
    * Log.print("              Purse class="
    * +cl+", adjustment="+adj+" (Purse="+purse+", race type="+raceType+")\n");
    * p.variant += adj; } else { if (Log.isDebug(Log.TRACE))
    * Log.print("              Race type not found, assuming same. (Purse="
    * +purse+", race type="+raceType+")\n"); } } }
    */
   if (Log.isDebug(Log.TRACE))
    Log.print("              Total adjustment=" + p.variant + " (1/5ths)\n");
   p.as += p.variant;
   if (Log.isDebug(Log.TRACE))
    Log.print("              Computed AS=" + p.as + "\n");
   // Select Running line rep race
   // Pick the race with the best speed rating to be used
   // with Running line and AS.
   // If there is no rep race for a horse, the running
   // line will produce all zeroes.
   if (m_repRace == null || p.as > m_repRace.as) {
    m_repRace = p;
   }
   // Select Recency rep race
   // Pick a second race with the best speed rating within
   // last 28 days to be used with Recency.
   if (!p.ppRaceDate.before(race.m_recencyDate)
     && (m_recency == null || p.as > m_recency.as)) {
    m_recency = p;
   }
  }
  // Adjust Representitive race
  if (m_repRace != null) {
   if (Log.isDebug(Log.TRACE))
    Log.print("    Rep Race: Track: " + m_repRace.ppTrack + " "
      + Lib.datetoa(m_repRace.ppRaceDate) + ", Race#" + m_repRace.ppRaceNo
      + ", AS=" + m_repRace.as + "\n");
   adjRunningLine(race, m_repRace);
  } else {
   if (Log.isDebug(Log.TRACE))
    Log.print("    Rep Race: (nothing found)\n");
  }
  // Adjust Recency
  if (m_recency != null) {
   if (Log.isDebug(Log.TRACE))
    Log.print("    Recency Race: Track: " + m_recency.ppTrack + " "
      + Lib.datetoa(m_recency.ppRaceDate) + ", Race#" + m_recency.ppRaceNo
      + ", AS=" + m_recency.as + "\n");
   adjRunningLine(race, m_recency);
  } else {
   if (Log.isDebug(Log.TRACE))
    Log.print("    Recency Race: (nothing found)\n");
  }
 }
 /**
  * Apply Variant to running line Apply the variant (in units of 1/5 sec) 20%
  * for first time. (FS) 50% for second time, (SS) and 100% to the final
  * time.(FT) The TT and CS are obtained by subtracting.
  */
 private void adjRunningLine(Race race, Performance p)
 {
  double[] fraction = new double[3];
  double[] lengths = new double[3];
  // First Call
  fraction[0] = Lib.atof(p.m_props.getProperty("FRACTION1")); // time of the
                                                              // leader
  lengths[0] = Lib.atof(p.m_props.getProperty("LENGTHS1")); // lengths behind
                                                            // leader
  // Second Call
  fraction[1] = Lib.atof(p.m_props.getProperty("FRACTION2")); // time of the
                                                              // leader
  lengths[1] = Lib.atof(p.m_props.getProperty("LENGTHS2")); // lengths behind
                                                            // leader
  // Third Call
  fraction[2] = Lib.atof(p.m_props.getProperty("FRACTION3")); // time of the
                                                              // leader
  lengths[2] = Lib.atof(p.m_props.getProperty("LENGTHS3")); // lengths behind
                                                            // leader
  // At finish
  double fraction4 = Lib.atof(p.m_props.getProperty("FINALTIME")); // time of
                                                                   // winner
  String valueT = p.m_props.getProperty("LENGTHS4");
  double lengths4 = Lib.atof(p.m_props.getProperty("LENGTHS4")); // lengths
                                                                 // behind
                                                                 // winner
  if (!p.isRoute) {
   // ********* SPRINT ********************
   int firstCall = 0;
   int secondCall = 1;
   // For the first call, use the one that is between 19.0 and 25.0
   if (fraction[0] >= 19.0 && fraction[0] <= 25.0)
    firstCall = 0;
   else if (fraction[1] >= 19.0 && fraction[1] <= 25.0)
    firstCall = 1;
   // For the second call, it will use the fraction that is between 43 and 52.
   if (fraction[1] >= 43.0 && fraction[1] <= 52.0)
    secondCall = 1;
   else if (fraction[2] >= 43.0 && fraction[2] <= 52.0)
    secondCall = 2;
   p.fs = fraction[firstCall] + lengths[firstCall] * 0.2;
   p.ss = fraction[secondCall] + lengths[secondCall] * 0.2;
   p.ft = fraction4 + lengths4 * 0.2;
   // Distance adjustments
   double adj = distanceAdjustments(race.m_age, race.m_distance, p);
   p.ft += adj;
   p.tt = p.ss - p.fs;
   p.cs = p.ft - p.ss;
   if (Log.isDebug(Log.TRACE)) {
    Log.print("           SPRINT\n");
    Log.print("           First  Call:  leader Time=" + fraction[firstCall]
      + ", Lengths behind=" + lengths[firstCall] + "\n");
    Log.print("           Second Call:  leader Time=" + fraction[secondCall]
      + ", Lengths behind=" + lengths[secondCall] + "\n");
    Log.print("           Finish:       leader Time=" + fraction4
      + ", Lengths behind=" + lengths4 + "\n");
    if (adj != 0)
     Log.print("           Final time adjusted by " + adj
       + " sec for distance difference (" + toF(p.ppDistance) + " to "
       + toF(race.m_distance) + ")\n");
    Log.print("           Running line:     FS=" + Lib.ftoa(p.fs, 2) + ", SS="
      + Lib.ftoa(p.ss, 2) + ", FT=" + Lib.ftoa(p.ft, 2) + ", TT="
      + Lib.ftoa(p.tt, 2) + ", CS=" + Lib.ftoa(p.cs, 2) + "\n");
   }
  } else {
   // **********ROUTE**********************
   int firstCall = 1;
   int secondCall = 2;
   // For the first call, use the one that is between 44.0 and 60.0
   if (fraction[0] >= 44.0 && fraction[0] <= 60.0)
    firstCall = 0;
   else if (fraction[1] >= 44.0 && fraction[1] <= 51.0)
    firstCall = 1;
   // For the second call, it will use the fraction that is between 1:08 and
   // 1:20.
   if (fraction[1] >= 67.0 && fraction[1] <= 80.0)
    secondCall = 1;
   else if (fraction[2] >= 67.0 && fraction[2] <= 80.0)
    secondCall = 2;
   p.fs = fraction[firstCall] + lengths[firstCall] * 0.2;
   p.ss = fraction[secondCall] + lengths[secondCall] * 0.2;
   p.ft = fraction4 + lengths4 * 0.2;
   // Distance adjustments
   double adj = distanceAdjustments(race.m_age, race.m_distance, p);
   p.ft += adj;
   p.tt = p.ss - p.fs;
   p.cs = p.ft - p.ss;
   if (Log.isDebug(Log.TRACE)) {
    String text[] = { "First ", "Second", "Third " };
    Log.print("           ROUTE\n");
    Log.print("           " + text[firstCall] + " Call:  leader Time="
      + fraction[firstCall] + ", Lengths behind=" + lengths[firstCall] + "\n");
    Log
      .print("           " + text[secondCall] + " Call:  leader Time="
        + fraction[secondCall] + ", Lengths behind=" + lengths[secondCall]
        + "\n");
    Log.print("           Finish:       leader Time=" + fraction4
      + ", Lengths behind=" + lengths4 + "\n");
    if (adj != 0)
     Log.print("           Final time adjusted by " + adj
       + " sec for distance difference (" + toF(p.ppDistance) + " to "
       + toF(race.m_distance) + ")\n");
    Log.print("           Running line:     FS=" + Lib.ftoa(p.fs, 2) + ", SS="
      + Lib.ftoa(p.ss, 2) + ", FT=" + Lib.ftoa(p.ft, 2) + ", TT="
      + Lib.ftoa(p.tt, 2) + ", CS=" + Lib.ftoa(p.cs, 2) + "\n");
   }
  }
  p.fs = p.fs - (p.variant * 0.20) * 0.20;
  p.ss = p.ss - (p.variant * 0.20) * 0.50;
  p.ft = p.ft - (p.variant * 0.20);
  p.tt = p.ss - p.fs;
  p.cs = p.ft - p.ss;
  if (Log.isDebug(Log.TRACE))
   Log.print("           Adj Running line: FS=" + Lib.ftoa(p.fs, 2) + ", SS="
     + Lib.ftoa(p.ss, 2) + ", FT=" + Lib.ftoa(p.ft, 2) + ", TT="
     + Lib.ftoa(p.tt, 2) + ", CS=" + Lib.ftoa(p.cs, 2) + "\n");
 }
 /**
  * Weight adjustments: The weight difference is weight of todays race minus the
  * weight of the candidate race.
  *
  * For sprints (7.5F and less) if (weight < 119) Add (weight difference / 3) to
  * Variant. else Add (weight difference / 2) to Variant.
  *
  * For routes (8F and up) if (weight < 119) Add (weight difference / 2) to
  * Variant. else Add weight difference to Variant.
  *
  * (Note the weight difference will be negative if todays weight is lighter
  * than the rep race (dropping weight) so when we add the negative number to
  * the Variant it actually subtracts.
  *
  * @param weight
  *         - todays race's weight
  * @param p
  *         - a previous performance for this horse.
  * @return the adjustment to the variance (1/5ths).
  *
  *         When we get to adjustments to be added to the Variant - as related
  *         to Track Class, Purse Class, Weight (and doubtfully distance), all
  *         of these modifiers are in fifths of a second. Using weight as an
  *         example, in a sprint race up to 119# - from 110# to 119# - weight
  *         makes the least difference. Three pounds will slow a horse a fifth
  *         of a second. Most jockeys weigh 114-119. So almost all of this
  *         weight is the jockey, which we call "live weight). But, if a horse
  *         is assigned 122#, and the jockey weighs 114#, the horse must carry
  *         eight pounds in a saddle cloth. This is dead weight, and much more
  *         fatiguing. So, in sprints, if adding weight, we add 1/5 for every
  *         three pounds or fraction thereof up to 119#. Above 119#, we add 1/5
  *         for each additional two pounds or fraction thereof. If dropping
  *         weight, the same principle, just reverse the polarity - a deduction
  *         rather that an addition to the Variant.
  *
  *         In routes, we +/- 1/5 for every two pounds added up to 119#, and 1/5
  *         for every pound over 119#. Yes, this hinges on thirty-plus years of
  *         watching over 100,000 races.
  *
  *         Example: A horse is DROPPING three pounds - say from 118# to 115#.
  *         This should theoretically let the horse run one-fifth FASTER, so the
  *         adjustment should be -1 instead of +1. Anything suggesting the horse
  *         speeds up is SUBTRACTED from the Variant. Anything suggesting that
  *         the horse will run SLOWER is added to the Variant.
  */
 private double weightAdjustments(int weight, Performance p)
 {
  int pWeight = Lib.atoi(p.m_props.getProperty("PPWEIGHT"));
  if (pWeight == 0 || weight == 0)
   return 0;
  int dif1 = 0;
  int dif2 = 0;
  if (pWeight < 120) {
   if (weight < 120)
    dif1 = weight - pWeight;
   else { // weight is above pWeight is below
    dif1 = 120 - pWeight;
    dif2 = weight - 120;
   }
  } else if (weight >= 120)
   dif2 = weight - pWeight;
  else { // pWeight is above and weight is below
   dif1 = 120 - weight;
   dif2 = pWeight - 120;
  }
  double adj = 0;
  if (!isRoute(p)) {
   // For Sprints
   adj = ((double) -dif1) / 3.0;
   adj += ((double) -dif2) / 2.0;
  } else {
   // For Routes
   adj = ((double) -dif1) / 2.0;
   adj += ((double) -dif2);
  }
  if (Log.isDebug(Log.TRACE))
   Log.print("              Weight Adjustment=" + adj + ",  (Weight=" + pWeight
     + " to " + weight + ") " + dif1 + "lb and " + dif2 + "lb\n");
  return adj;
 }
 /**
  * (1) 5f - at present we use 5f, 5.5f, and 6f. This a race that starts on the
  * main track just before the turn. These races are typically a "dash for the
  * cash" - first horse out of the gate can have a big advantage. There is not
  * enough distance for an experienced horse to close a lot of ground. Also,
  * many races at this distance are for young (and/or "cheap") horses that don't
  * really know what they are doing.
  *
  * 5.5f - also starts on the main track (not from a "chute"), and involves many
  * of the characteristics of the 5F race. I am increasingly reluctant to use a
  * representative race that is not also a "short sprint". At present, a rep
  * race for 5.5f can be 5f, 6f, or 6.5f.
  *
  * I am inclined to favor TRULINE 2000 to shift gears - if today's race is 5f
  * or 5.5f, the rep race must be 5f or 5.5f. If today's race was at 5.5F, and
  * the rep race is at 5f, we would add six SECONDS to the final time. If
  * today's race was at 5F, and we used a rep race at 5.5F, we would subtract
  * six seconds.
  *
  * (2) MAJOR CLARIFICATION - in a sprint, the first two calls are quarter mile
  * and half mile. In a route, the second and third calls are the half mile and
  * six furlong points. These distances do not change, regardless of the total
  * distance of the race. The amount of a horse's energy that is expended in
  * these calls is the same regardless of the total distance and final time. So
  * these "distance adjustments" are limited to final time only.
  *
  * (3) If the race is at 6f or 6.5F, at present we use any distance that is
  * within one eighth of a mile. I am inclined for a race at 6F to limit to 6f
  * and 6.5f. The race at 7f is within 1/8 mile. But I find 7f to be a problem
  * distance. Particularly, jockeys don't ride this distance enough, and too
  * many do not know how to rate the horse appropriately. They either give the
  * horse the same cues as for six furlongs, or else they give the cues for 8f -
  * both of which are wrong.
  *
  * To explain, if a horse is to sustain his energy for the entire race, he must
  * "change leads" at specific points. If he led with the same front leg
  * throughout, he would exhaust himself much quicker, always extending the one
  * leg full out and taking the brunt of the surface contact, instead of
  * dividing this between the front legs.
  *
  * IF Michael agreed, then we would deal - 6f vs 6.5f - with a distance
  * adjustment of six and three-fifth seconds (33 fifths). But these final times
  * adjustments would not effect the modified Variant. Variant is limited to the
  * other things that involve the horse's fatigue factor and competitive
  * ability.
  *
  * (4) If today's race is at 7F, I am nervous at using any distance but today's
  * distance. This, again, is a big change from the present program, where we go
  * +/- 1/8 of a mile. THIS, however, is a matter of philosophy, and I would not
  * want to superimpose my reaction arbitrarily. Michael - what do you think?
  * And should we ask other Users for their opinion.
  *
  * (5) If, usually rare occasions, today's race is at 7.5f, punt. Excuse my
  * French, but this is a bastard distance. At most, I would limit to 7F and 8F
  * for the rep race. Would limit to .5 differential.
  *
  * (6) 8f is what is referred to as a "middle distance". Quite a few horses
  * that are sprint-bred can stretch out this far (with the right trainer and
  * conditioning), where rarely can they go farther. Similarly, some route-bred
  * horses can shorten up this much, but do not have enough pure speed to handle
  * shorter distances. Unfortunately, in current time, we see more and more
  * races at 8f, carded to accommodate route horses that are injured, and cannot
  * sustain anymore the longer distances.
  *
  * If today's race is at 8f, I would probably support a rep race from 7f (with
  * a thirteen-second change to final time), as well as a rep race at 8.5 (six
  * and three-fifths second difference) and 9F (also thirteen seconds
  * difference).
  *
  * (7) If today's race is at 8.5f, we now use +/- 1/8. I favor a change to +/-
  * 1/16. Michael - remember, you have an overriding vote in all matters. I rely
  * on your judgement immensely.
  *
  * (8) If today's race is at 9F, we can use +/- 1/8, which can involve either a
  * +/- seven seconds (if using 8.5 or 9.5 races) or +/- thirteen seconds (if
  * using 8f or 10f).
  *
  * (9) If today's race is 10f or longer, there is a major problem in that the
  * entire running line changes. The first call is a half mile, the second call
  * is one mile. Basically, the way I think we should deal with this is - if
  * today's race is 10F or longer, we arbitrarily handicap it as a race at 9.5f.
  * Primarily, we are using as rep races 8.5F and 9F - using "extensions" of
  * seven seconds and thirteen seconds. (Note: if we handicapped the longer
  * routes with the same philosophy, ALL horses entered would have few to none
  * rep races to consider because these longer races are few except for Graded
  * Stakes. The present TRULINE has shown that using 9.5f works.
  *
  * Distance Adjustments: If we are only going to compare same-length races then
  * we don't need to do any length adjustments except: Todays Race Rep Race
  * Final Time DRF (seconds) SPEED RATING 5.0F 5.0F 0 0 5.5F -6 0 6.0F -12 +5
  * (no 2yr olds) 5.5F 5.0F +6 0 5.5F 0 0 6.0F -6.3 +7 (no 2yr olds) 6.0F 5.5F
  * +6.3 -7 6.0F 0 0 6.5F -6.3 0 7.0F -13 0 6.5F 6.0F +6.3 0 6.5F 0 0 7.0F -7 0
  * 7.0F 7.0F 0 7.5F 7.5F 0 8.0F 8.0F 0 8.3F -4 8.5F -6.6 9.0F -13 8.3F 8.0F +4
  * 8.3F 0 8.5F -3 8.5F 8.0F +6.6 8.3F +3 8.5F 0 9.0F -6.6 9.0F 8.0F +13 8.5F +7
  * 9.0F 0 9.5F -7 10.0F -13 9.5F 8.5F +13 9.0F +7 9.5F 0 for 10F and up, treat
  * it as a 9.5F race.
  *
  * @return the final time adjustment in seconds. Return 999 if not allowed.
  *         NOTE: adjustment to DRF SPEED RATING is performed in
  *         determineRepRace()
  */
 private double distanceAdjustments(String age, int distance, Performance p)
 {
  double adj = 999;
  switch (distance) {
   case F5_0:
    switch (p.ppDistance) {
     case F5_0:
      adj = 0;
      break;
     case F5_5:
      adj = -6.0;
      break;
     case F6_0:
      if (!age.startsWith("A")) // not 2 year olds
       adj = -12.0;
      break;
    }
    break;
   case F5_5:
    switch (p.ppDistance) {
     case F5_0:
      adj = +6.0;
      break;
     case F5_5:
      adj = 0;
      break;
     case F6_0:
      if (!age.startsWith("A")) // not 2 year olds
       adj = -6.3;
      break;
    }
    break;
   case F6_0:
    switch (p.ppDistance) {
     case F5_0:
      adj = +12;
      break;
     case F5_5:
      adj = +6.3;
      break;
     case F6_0:
      adj = 0;
      break;
     case F6_5:
      adj = -6.3;
      break;
     case F7_0:
      adj = -13;
      break;
    }
    break;
   case F6_5:
    switch (p.ppDistance) {
     case F5_5:
      adj = +12;
      break;
     case F6_0:
      adj = +6.3;
      break;
     case F6_5:
      adj = 0;
      break;
     case F7_0:
      adj = -7;
      break;
     case F7_5:
      adj = -13;
      break;
    }
    break;
   case F7_0:
    switch (p.ppDistance) {
     case F6_0:
      adj = +12;
      break;
     case F6_5:
      adj = +6.3;
      break;
     case F7_0:
      adj = 0;
      break;
     case F7_5:
      adj = -6.6;
      break;
    }
    break;
   case F7_5:
    switch (p.ppDistance) {
     case F6_5:
      adj = +12;
      break;
     case F7_0:
      adj = +6.3;
      break;
     case F7_5:
      adj = 0;
      break;
    }
    break;
   case F8_0:
    switch (p.ppDistance) {
     case F8_0:
      adj = 0;
      break;
     case F8_1:
      adj = -2.4;
      break;
     case F8_3:
      adj = -4.2;
      break;
     case F8_5:
      adj = -6.6;
      break;
     case F9_0:
      adj = -13.1;
      break;
    }
    break;
   case F8_1:
    switch (p.ppDistance) {
     case F8_0:
      adj = +2.4;
      break;
     case F8_1:
      adj = 0;
      break;
     case F8_3:
      adj = -1.8;
      break;
     case F8_5:
      adj = -4.2;
      break;
     case F9_0:
      adj = -10.8;
      break;
    }
    break;
   case F8_3:
    switch (p.ppDistance) {
     case F8_0:
      adj = +4.2;
      break;
     case F8_1:
      adj = +1.8;
      break;
     case F8_3:
      adj = 0;
      break;
     case F8_5:
      adj = -2.4;
      break;
     case F9_0:
      adj = -9.2;
      break;
    }
    break;
   case F8_5:
    switch (p.ppDistance) {
     case F8_0:
      adj = +6.6;
      break;
     case F8_1:
      adj = +4.2;
      break;
     case F8_3:
      adj = +2.4;
      break;
     case F8_5:
      adj = 0;
      break;
     case F9_0:
      adj = -6.6;
      break;
     case F9_5:
      adj = -13.0;
      break;
    }
    break;
   case F9_0:
    switch (p.ppDistance) {
     case F8_0:
      adj = +13.0;
      break;
     case F8_1:
      adj = +11.5;
      break;
     case F8_3:
      adj = +9.5;
      break;
     case F8_5:
      adj = +7;
      break;
     case F9_0:
      adj = 0;
      break;
     case F9_5:
      adj = -7;
      break;
     case F10:
      adj = -13.0;
      break;
    }
    break;
   case F9_5:
    switch (p.ppDistance) {
     case F8_5:
      adj = +13.0;
      break;
     case F9_0:
      adj = +7;
      break;
     case F9_5:
      adj = 0;
      break;
     case F10:
      adj = -7;
      break;
    }
    break;
   case F10:
    switch (p.ppDistance) {
     case F9_0:
      adj = +13.0;
      break;
     case F9_5:
      adj = +7;
      break;
     case F10:
      adj = 0;
      break;
    }
    break;
   default:
    if (distance > F10) {
     switch (p.ppDistance) {
      case F9_0:
       adj = +13.0;
       break;
      case F9_5:
       adj = +7;
       break;
      case F10:
       adj = 0;
       break;
      default:
       if (p.ppDistance == distance)
        adj = 0;
     }
    }
    break;
  }
  // if (Log.isDebug(Log.TRACE))
  // Log.print("              Distance Adjustment="+((adj==999)?"DIFFERENT":(adj+""))+", this race="+distance+"yards("+toF(distance)+"),  ppdistance="+p.ppDistance+"yards("+toF(p.ppDistance)+")\n");
  return adj;
 }
 /**
  * Search the race class table (truline.rc) and locate the entry with the same
  * race type and the closest purse value, rounding down.
  *
  * @param raceType
  *         - the type of the race.
  * @param purse
  *         - the purse amount.
  * @return race class. private static double getPurseClass(String raceType, int
  *         purse) { int p2; double purseClass = 0;
  *
  *         //Log.print("rc.size()="+Truline.rc.size()+", raceType="+raceType+
  *         " purse="+purse+"\n"); for(Enumeration c = Truline.rc.elements();
  *         c.hasMoreElements();) { Properties prop =
  *         (Properties)c.nextElement(); if
  *         (prop.getProperty("RACETYPE","").equals(raceType)) { p2 =
  *         Lib.atoi(prop.getProperty("PURSE","0")); purseClass =
  *         Lib.atof(prop.getProperty("CLASS","")); if (p2 >= purse) break; } }
  *         return purseClass; }
  */
 /**
  * QP is for Quirin points. The FIRST objective of speed points is to reflect
  * the alertness of a horse in the starting gate, and his readiness to leave
  * the gate when the bell sounds. If he is regularly to the first call either
  * first, or among the first three, this is a significant advantage, as in
  * many, many races - the race is won by the horse that is out of the gate
  * first - and goes "wire to wire". At many tracks and in certain weather and
  * track surface conditions, the first call is an important aspect of
  * prediction.
  *
  * Next of importance is how far he is from the leader. In a sprint race,
  * because the distance they are traveling is limited, it is hard to make up
  * ground. A horse that is within two lengths of the leader is close enough to
  * have a reasonable chance to make up the deficiency and catch the leader.
  * Conversely, if the horse is third by six lengths, that is a lot of ground to
  * make up in the last half mile.
  *
  * A good Thoroughbred at top speed runs at about forty miles per hour. A horse
  * that is six lengths behind would have go of 45 MPH in order to win. Most
  * horses are not capable of that speed.
  *
  * SO, in SPRINTS, we give him ONE POINT if being first, second or third at the
  * First Call. If he is near the leader (within two lengths), we give him a
  * SECOND POINT for proximity to the lead.
  *
  * The next test of the horse in the sprint is his ability to sustain. Think of
  * me in track and field. Fat and seventy, being alert I might be off the
  * blocks with any other runner. But I could not sustain the pace for long. The
  * vast majority of horses that DO start quickly fade between the first and
  * second Call, and are far back at the finish.
  *
  * So if the horse is first, second, or third at the SECOND CALL we give him
  * another POINT. And if he is within two lengths, he gets another POINT for
  * proximity. So, in the most recent race, depending on how he runs, he can
  * earn 0-1-2-3-4 POINTS. The same applies to his second race back, and also to
  * his third race back. This means a perfect score would be 12 - first AND
  * within two lengths at each Call in each of the three races.
  *
  * Oh, yes, if today's race is a dirt sprint, we use the last three dirt sprint
  * races.
  *
  * In the case of a route race, the distance of the race is long enough to
  * allow the horse to make up ground. We are still interested in the horse
  * being alert out of the starting gate, and would prefer him to be towards the
  * front. But the numbers of lengths is less meaningful.
  *
  * So, we give him a point for being first, second, or third at the first Call,
  * and a second point for being first, second, or third at the second Call - a
  * possible two points for his most recent ROUTE race. We do the same for the
  * second route race back, and also for the third back. This is a potential six
  * points. If the horse is first, second, or third at both Calls in all three
  * races, we give him a point for CONSISTENCY, which means a perfect score is
  * SEVEN.
  *
  * If today's race is a dirt route race, we use the last three dirt routes.
  * Whenever the race is a Turf race, sprint or route, we use only the last
  * three Turf races in the appropriate distance range.
  *
  * If we treat routes in this fashion, we will also be less vulnerable to any
  * variations in BRIS format.
  *
  * @param race
  *         - the race object
  * @param post
  *         - The post object for a horse in this race.
  * @return the number of Quirin points
  */
 private int quirinPoints(Race race, Post post)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("  Quirin Calculation for Post# " + post.m_postPosition
     + ", Horse: " + post.m_horseName + "\n");
  // Sort the past performances by race date.
  Performance.qSort(post.m_performances);
  int points = 0;
  int position;
  String pos0;
  String pos1;
  String pos2;
  double lengths;
  int cnt = 0;
  int rcount = 0;
  int scount = 0;
  for (int i = 0; i < post.m_performances.size(); i++) {
   Performance p = (Performance) post.m_performances.elementAt(i);
   // If todays race is dirt, look only at dirt. If turf, look only at turf.
   String pSurface = p.m_props.getProperty("PPSURFACE", "").toUpperCase();
   if (!race.m_surface.equals(pSurface)
     || p.isRoute != (race.m_distance >= F8_0))
    continue;
   if (!p.isRoute) {
    // For sprint races
    pos0 = p.m_props.getProperty("POSITION1", ""); // Position at Start Gate
    pos1 = p.m_props.getProperty("POSITION2", ""); // Position at First Call
    pos2 = p.m_props.getProperty("POSITION3", ""); // Position at Second Call
    String lenStr1 = p.m_props.getProperty("LENGTHS1", "999");
    String lenStr2 = p.m_props.getProperty("LENGTHS2", "999");
    if (pos1.equals("1"))
     lenStr1 = "0";
    if (pos2.equals("1"))
     lenStr2 = "0";
    if (Log.isDebug(Log.TRACE)) {
     Log.print("     " + p.ppTrack + " " + Lib.datetoa(p.ppRaceDate)
       + ", Race#" + p.ppRaceNo + ", Call#1: POS=" + pos1 + ", LEN=" + lenStr1
       + " Call#2: POS=" + pos2 + ", LEN=" + lenStr2 + " SPRINT\n");
     Log.print("         (Position at starting gate=" + pos0 + ")\n");
    }
    position = Lib.atoi(pos1);
    lengths = Lib.atof(lenStr1);
    if (position >= 1 && position <= 3) {
     points++; // One of the three front-runners
     scount++;
     if (Log.isDebug(Log.TRACE))
      Log
        .print("        One point for being one of three front-runners at call#1\n");
    }
    if (lengths <= 2.0) {
     scount++;
     points++; // within 2 lengths at this call
     if (Log.isDebug(Log.TRACE))
      Log.print("        One point for within 2 lengths at call#1\n");
    }
    position = Lib.atoi(pos2);
    lengths = Lib.atof(lenStr2);
    if (position >= 1 && position <= 3) {
     points++; // One of the three front-runners
     if (Log.isDebug(Log.TRACE))
      Log
        .print("        One point for being one of three front-runners at call#2\n");
    }
    if (lengths <= 2.0) {
     scount++;
     points++; // within 2 lengths at this call
     if (Log.isDebug(Log.TRACE))
      Log.print("        One point for within 2 lengths at call#2\n");
    }
   } else { // for Route races
    String lenStr1, lenStr2;
    lenStr1 = p.m_props.getProperty("LENGTHS1", "999");
    lenStr2 = p.m_props.getProperty("LENGTHS2", "999");
    pos0 = p.m_props.getProperty("POSITION1", ""); // Position at start gate
    pos1 = p.m_props.getProperty("POSITION2", ""); // Position at First Call
    pos2 = p.m_props.getProperty("POSITION3", ""); // Position at Second Call
    if (pos1.equals("1"))
     lenStr1 = "0";
    if (pos2.equals("1"))
     lenStr2 = "0";
    if (Log.isDebug(Log.TRACE)) {
     Log.print("     " + p.ppTrack + " " + Lib.datetoa(p.ppRaceDate)
       + ", Race#" + p.ppRaceNo + ", Call#1: POS=" + pos1 + ", LEN=" + lenStr1
       + " Call#2: POS=" + pos2 + ", LEN=" + lenStr2 + " ROUTE\n");
     Log.print("         (Position at starting gate=" + pos0 + ")\n");
    }
    // First Call
    position = Lib.atoi(pos1);
    if (position >= 1 && position <= 3) {
     rcount++; // leader at this call
     points++;
     if (Log.isDebug(Log.TRACE))
      Log
        .print("        One point for being one of three front-runners at call#1\n");
    }
    // Second Call
    position = Lib.atoi(pos2);
    lengths = Lib.atof(lenStr2);
    if (position >= 1 && position <= 3) {
     rcount++; // leader at this call
     points++;
     if (Log.isDebug(Log.TRACE))
      Log
        .print("        One point for being one of three front-runners at call#2\n");
    }
   }
   cnt++;
   if (cnt >= 3) // only look at the first three races of the same type
    break;
  }
  if (rcount == 6) {
   points++; // was front runner at both calls in all three races (ROUTE only)
   if (Log.isDebug(Log.TRACE))
    Log
      .print("        A bonus point for being 1,2,or 3 at both calls in all races\n");
  }
  // if (scount == 6)
  // points += 6; // was leader at both calls in all three races (Sprint only)
  if (Log.isDebug(Log.TRACE))
   Log.print("     Total Quirin Points=" + points + "\n");
  return points;
 }
 /**
  * Compute the EQ (Energy Quotient)
  *
  * Races - if he just runs around the track - last or middle of the pack - he
  * gets credit for the number of furlongs in the race - which is usually
  * 6-6.5-7-8-8.5-9-10. In such a race, there is no sign that he was extended -
  * reacting to the jockey's whip and urgings to run fast.
  *
  * BUT, if he runs in front for most of the race, even though he may not win,
  * he has been extended - he has made a muscle-building effort. Similarly, if
  * he started in the rear, and made up ground and passed other horses - even if
  * he missed the win, he was "all out" and this should bring improvement next
  * time.
  *
  * In both of these scenarios, we give him a 60% Bonus. We are going to look at
  * each of his last three efforts. We subtract the date of the third effort
  * back from today's date. That becomes the denominator. The numerator is the
  * number of lengths credit he is given. Below is "regular race" vs "extended
  * effort" credit that we give as the distance (which becomes the numerator).
  *
  * REGULAR EXTENDED
  *
  * 5.0 8.0 5.5 8.8 6.0 9.6 6.5 10.4 7.0 11.2 7.5 12.0 8.0 12.8 8.5 13.6 9.0
  * 14-4 10.0 16.0
  *
  * WORK-OUTS - For most work-outs the horse is credited with the length of
  * distance worked. The exception is when it is the fastest work-out of the day
  * for that distance/ In this case, we give a 40% Bonus. Such as:
  *
  * REGULAR EXTENDED
  *
  * 2.0 2.8 3.0 4.2 4.0 5.6 5.0 7.0 6.0 8.4 7.0 9.8 8.0 11.2
  *
  *
  * Now let me hypothesize. Today's race date is 8/14. The horse was second in a
  * six furlong race on 7/10. He raced mid-pack for 8.5 furlongs on 8/1. On 8/10
  * he worked five furlongs. There is no "bullet" or "cannonball" to indicate
  * the best work-out of the dat.
  *
  * >From 7/10 to 8/14 is 35 days. He gets 9.6 lengths credit for a competitive
  * six furlongs on 7/10. For 8/1, he loafed along, and gets just the distance
  * of the race, 8.5 furlongs. Likewise he gets five lengths credit for a
  * routine work-out on 8/10. These three efforts total 23.1, which we divide by
  * 35. This gives him a .66 for the first leg of three measurements.
  *
  * Now we look at the three efforts in relationship excluding today's date.
  * (Did he have so much exertion that he needed a rest?) So we subtract 7/10
  * from 8/10, 31 days, and again divide into the 23.1 lengths credit. For this
  * he gets .745.
  *
  * Now we look at recency. Subtracting the date of the last effort from today's
  * date, we get 4 days. This is divided into the 5.0 furlongs worked, which
  * gives him a 1.25. Adding the three values, his ENERGY NUMBER is 2.655, which
  * we round up to 2.7.
  *
  * Now let's look at another horse. On 7/1, he closed ten lenths to be second
  * by two at 8.5 furlongs (bonus). On 7/15 he wins at 6f (bonus). On 8/5, he
  * leads to the head of the stretch, and finishes third by a length (bonus).
  *
  * He receives 13.6 plus 9.6 plus 13.6 lengths credit, a total of 36.8. Divided
  * by 44 days, he receives .836. Taking the efforts in themselves, we divide 35
  * days into 36.8 and get 1.05. Then, for recency, we divide nine days into
  * 13.6 lengths and get 1.51. The three values total toan ENERGY NUMBER of
  * 3.396, which we round up to 3.4.
  *
  *
  * For each horse in a race: { Select EQ Races/workouts =============== for the
  * last 3 races or workouts { If this was a real race if within first 3
  * positions or within 2 lengths of leader at finish multiply distance by 1.6
  * If this is a workout set extra flag to workout type (not sure what this
  * does) if the extra flag is set, multiply distance by 1.4 }
  *
  * Compute EQ =========== EqKey1 = average distance per day over the period of
  * the workouts. EqKey2 = average distance per day from first workout to race
  * day. EqKey3 = average distance per day from last workout to race day.
  * EnergyKeyTotal = EqKey1 + EqKey2 + EqKey3; Energy Ranking is based on this
  * number. }
  *
  */
 private double computeEQ(Race race, Post post)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("  Energy Calculation for Post# " + post.m_postPosition
     + ", Horse: " + post.m_horseName + "\n");
  // sort the workouts by work date, (most recent first).
  // Assume unused m_work elements contain null dates.
  int len;
  for (len = 0; len < post.m_work.length; len++) {
   if (post.m_work[len] == null || post.m_work[len].m_workDate == null)
    break;
  }
  // for (int i = 0; i < 12; i++)
  // {
  // if (post.m_work[i] != null && post.m_work[i].m_workDate != null)
  // Log.print("          presort: "+post.m_work[i].m_props+"\n");
  // }
  for (int i = 0; i < len; i++) {
   for (int j = i + 1; j < len; j++) {
    // Compare keys
    Workout a = post.m_work[i];
    Workout b = post.m_work[j];
    if (a.m_workDate.before(b.m_workDate)) {
     // Swap elements
     post.m_work[i] = b;
     post.m_work[j] = a;
    }
   }
  }
  if (Log.isDebug(Log.TRACE)) {
   for (int i = 0; i < len; i++) {
    Properties props = post.m_work[i].m_props;
    double d = Lib.atof(props.getProperty("WORKDISTANCE"));
    boolean bullet = false;
    String rank = props.getProperty("WORKRANK", "");
    Log.print("              Workdate: "
      + Lib.datetoa(post.m_work[i].m_workDate) + ", distance=" + toF(d)
      + " work rank=" + rank + "\n");
   }
  }
  // We assume performances are already sorted by date (most recent first).
  // Select EQ Races/workouts from the last 3 races or workouts
  //
  double distance = 0; // total distance
  double lastd = 0; // most recent distance
  Date first = null; // First workout or race (oldest)
  Date last = null; // Last workout or race (one just before race)
  int raceIdx = 0; // race index
  int workoutIdx = 0; // workout index
  for (int i = 0; i < 3; i++) {
   Performance p = null;
   if (raceIdx < post.m_performances.size())
    p = (Performance) post.m_performances.elementAt(raceIdx);
   if (workoutIdx < len
     && (raceIdx >= post.m_performances.size() || post.m_work[workoutIdx].m_workDate
       .after(p.ppRaceDate))) {
    // Use the workout.
    if (i == 0)
     last = post.m_work[workoutIdx].m_workDate;
    first = post.m_work[workoutIdx].m_workDate;
    Properties props = post.m_work[workoutIdx].m_props;
    boolean flg = false;
    if (props.getProperty("WORKRANK", "").equals("1"))
     flg = true; // it was a bullet race.
    double d = Lib.atof(props.getProperty("WORKDISTANCE"));
    if (Log.isDebug(Log.TRACE)) {
     Log.print("          EQ" + (i + 1) + " workout " + Lib.datetoa(first)
       + ", distance=" + toF(d) + ", work rank="
       + props.getProperty("WORKRANK", "") + ((flg) ? ", (bullet)" : " ")
       + "\n");
     Log.print("             DATA: " + props + "\n");
    }
    distance += ((flg) ? (d * 1.4) : d);
    if (i == 0)
     lastd = ((flg) ? (d * 1.4) : d);
    workoutIdx++;
   } else if (raceIdx < post.m_performances.size()) {
    // Use Race.
    if (i == 0)
     last = p.ppRaceDate;
    first = p.ppRaceDate;
    boolean flg = false;
    String lenStr = p.m_props.getProperty("LENGTHS4", "999"); // lengths at
                                                              // finish
    int pos = Lib.atoi(p.m_props.getProperty("POSITION6", "")); // Position at
                                                                // Finish
    if (Lib.atof(lenStr) <= 2.0 || (pos >= 1 && pos <= 3))
     flg = true;
    // NOTE: if a horse does not finish, its position will be 0, length=999
    if (Log.isDebug(Log.TRACE)) {
     String comment = p.m_props.getProperty("COMMENT", "");
     Log.print("          EQ" + (i + 1) + " Race " + Lib.datetoa(p.ppRaceDate)
       + ", distance=" + toF(p.ppDistance) + ", lengths="
       + ((lenStr.equals("999")) ? "0" : lenStr) + ", pos=" + pos
       + ((flg) ? ", (Extra Flag) \"" : " \"") + comment + "\"\n");
    }
    distance += ((flg) ? (p.ppDistance * 1.6) : p.ppDistance);
    if (i == 0)
     lastd = ((flg) ? (p.ppDistance * 1.6) : p.ppDistance);
    raceIdx++;
   } else
    break; // no more data.
  }
  double energy = 0;
  if (first != null && last != null) {
   int days1 = Lib.dateDiff(first, last); // days in period of workouts.
   int days2 = Lib.dateDiff(first, race.m_raceDate); // first workout to race
                                                     // date
   int days3 = Lib.dateDiff(last, race.m_raceDate); // last workout to race
                                                    // date.
   double eqkey1 = 0;
   double eqkey2 = 0;
   double eqkey3 = 0;
   if (days1 > 0)
    eqkey1 = distance / (double) days1 / (double) YdPerF;
   if (days2 > 0)
    eqkey2 = distance / (double) days2 / (double) YdPerF;
   if (days3 > 0)
    eqkey3 = lastd / (double) days3 / (double) YdPerF;
   energy = eqkey1 + eqkey2 + eqkey3;
   extraFlg = (energy > 4.6);
   if (Log.isDebug(Log.TRACE)) {
    Log.print("        Resulting distance credit=" + toF(distance) + "\n");
    Log.print("        first workout/race to last workout/race (" + days1
      + " days) distance/day=" + Lib.ftoa(eqkey1, 3) + "\n");
    Log.print("        first workout to race day (" + days2
      + " days) distance/day=" + Lib.ftoa(eqkey2, 3) + "\n");
    Log.print("        last workout to race day (" + days3
      + " days) distance/day=" + Lib.ftoa(eqkey3, 3) + "\n");
    Log.print("        Total energy figure: " + Lib.ftoa(energy, 3) + "\n");
    if (extraFlg)
     Log.print("        This horse gets an (*).\n");
   }
  } else if (Log.isDebug(Log.TRACE))
   Log.print("        Nothing to base energy figure on.\n");
  return energy;
 }
 /********************************************
  * Earnings Computations (EPS) ===================== For each horse in a race:
  * { Earnings Ratio = Total Earnings / Starts; Use the Horse's Lifetime Record:
  * }
  *
  */
 private double computeEPS(Race race, Post post)
 {
  double ratio;
  if (Log.isDebug(Log.TRACE))
   Log.print("  Earnings Ratio for Post# " + post.m_postPosition + ", Horse: "
     + post.m_horseName + "\n");
  int starts;
  double earnings;
  if (race.m_surface.equals("T")) {
   // use only lifetime Turf stats
   starts = Lib.atoi(post.m_props.getProperty("LRTURFSTARTS"));
   earnings = Lib.atof(post.m_props.getProperty("LRTURFEARNINGS"));
  } else {
   // use all stats
   starts = Lib.atoi(post.m_props.getProperty("LIFETIMESTARTS"));
   earnings = Lib.atof(post.m_props.getProperty("LIFETIMEEARNINGS"));
  }
  if (starts == 0)
   ratio = 0;
  else
   ratio = earnings / starts;
  if (Log.isDebug(Log.TRACE)) {
   NumberFormat nf = NumberFormat.getCurrencyInstance();
   Log.print("        earnings=" + nf.format(earnings) + "  starts=" + starts
     + "  EPS=" + Lib.ftoa(ratio, 2)
     + ((race.m_surface.equals("T")) ? " (Turf only)" : "") + "\n");
  }
  return ratio;
 }
 /*******************************************************
  * Rank Horses by EPS, EN, FS, TT, SS, CS, FT, AS, RE, QP.
  *
  * I think we are about at the point of settting up the computations. The
  * highest EPS # should be #1 ranked. Each next highest number gets the next
  * ranking. EPS would ultimately be a potential 1-16. If a horse has no EPS, it
  * is tied for last.
  *
  * EN also goes from highest number to lowest number in the ranking. Any horse
  * with an EN over 4.6 should have an asterisk (*).
  *
  * FS, TT, SS, FT, and CS - the fastest fraction is #1 ranked, next fastest #2.
  *
  * AS, RE and QP - the highest number is #1, the next highest #2, etc.
  */
 private static void rankHorses(Race race)
 {
  // Handicap rankings
  if (Log.isDebug(Log.TRACE))
   Log.print("  Rank Horses in race #" + race.m_raceNo + "\n");
  Vector[] ranking = new Vector[names.length];
  for (int i = 0; i < ranking.length; i++)
   ranking[i] = new Vector(); // Vectors contain Post objects for the horse.
  // Rank each horse in the race.
  race.m_cnthorses = 0;
  race.m_cntnrl = 0;
  race.m_cnt1st = 0;
  race.m_cntnrlML = 0;
  race.m_cnttrnown = 0;
  Boolean entryA = false;
  Boolean entryB = false;
  Boolean entryC = false;
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String scratch = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || scratch.equals("S"))
    continue; // position is empty or scratched
   if (scratch.equals("A")) {
    if (entryA == false) {
     entryA = true;
     race.m_cnthorses++;
    }
   }
   else if (scratch.equals("B")) {
    if (entryB == false) {
     entryB = true;
     race.m_cnthorses++;
    }
   }
   else if (scratch.equals("C")) {
    if (entryC == false) {
     entryC = true;
     race.m_cnthorses++;
    }
   }
   else
    race.m_cnthorses++;

   Boolean noFS = false;
   for (int param = 0; param < names.length; param++) {
    int i;
    boolean found = false;
    if (param == FS & post.m_handicap.value[param] == 0) {
     race.m_cntnrl++;
     noFS = true;
     if (!post.m_morningLine.equals("")) {
      switch (post.m_morningLine.substring(0, 2)) {
       case "1-":
       case "2-":
       case "3-":
       case "4-":
        // case "5-":
        race.m_cntnrlML++;
      }
      switch (post.m_morningLine) {
       case "6-5":
       case "7-5":
       case "8-5":
       case "9-5":
       case "7-2":
        // case "9-2":
        race.m_cntnrlML++;
      }
     }
    }
    if (param == EPS & post.m_handicap.value[param] == 0 & noFS) {
     race.m_cnt1st++;
    }
    if (post.m_handicap.value[param] > 0) {
     if (param < AS) {
      // Running line - smallest at top (but 0 sorts to bottom)
      for (i = 0; i < ranking[param].size(); i++) {
       Post p = (Post) ranking[param].elementAt(i);
       if (p.m_handicap.value[param] <= 0
         || post.m_handicap.value[param] < p.m_handicap.value[param]) {
        ranking[param].insertElementAt(post, i);
        found = true;
        break;
       }
      }
     } else {
      // Other parameters - largest at top
      for (i = 0; i < ranking[param].size(); i++) {
       Post p = (Post) ranking[param].elementAt(i);
       if (post.m_handicap.value[param] > p.m_handicap.value[param]) {
        ranking[param].insertElementAt(post, i);
        found = true;
        break;
       }
      }
     }
    }
    if (!found)
     ranking[param].addElement(post);
   }
  }
  // ////////////// ASSIGN RANKINGS ///////////////////////////////
  if (race.m_cnthorses > 0) {
   for (int param = 0; param < names.length; param++) {
    if (Log.isDebug(Log.TRACE))
     Log.print("\n  Rankings for " + names[param] + "\n");
    Post top = (Post) ranking[param].elementAt(0);
    int lastrank = 1;
    double lastvalue = top.m_handicap.value[param];
    for (int i = 0; i < ranking[param].size(); i++) {
     Post p = (Post) ranking[param].elementAt(i);
     if (p.m_handicap.value[param] == 0)
      p.m_handicap.rank[param] = ranking[param].size();
     else if (p.m_handicap.value[param] == lastvalue)
      p.m_handicap.rank[param] = lastrank; // look for top or ties.
     else
      p.m_handicap.rank[param] = i + 1;
     if (Log.isDebug(Log.TRACE))
      Log.print("    #" + p.m_handicap.rank[param] + " " + names[param] + "="
        + Lib.ftoa(p.m_handicap.value[param], 2) + " Post# " + p.m_postPosition
        + ", Horse: " + p.m_horseName + "\n");
     lastrank = p.m_handicap.rank[param];
     lastvalue = p.m_handicap.value[param];
    }
   }
  }
 }
 /***************************
  * Assign Points: Once the rankings are given, we need to assign a point value
  * for #1-2-3-4-etc. in each column. This is subject to negotiation. Right now,
  * #1 EPS is given 8 points, #2 - 4 points, #3 1 point, all the others = 0
  * points. #1 EN gets 4 points, #2 - 2 points, #3 - 1 points, all others are 0.
  * If the number is over 4.6, give another 4 points.
  *
  * For FS, TT, SS, CS, FT, AS, and QP - TRULINE gives 4 points for #1, 2 points
  * for #2, 1 point for #3 and 0 to all others. RE gives 8 points to #1, 4
  * points for #2, 1 point for #3, and 0 for the others. Querying may point us
  * to very different impact values. Even common sense and discussion will
  * probably suggest some changes.
  *
  * @param race
  *         - The race being handicapped.
  * @return list of Posts in order by bonus point rankings.
  */
 private static void assignPoints(Race race)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Assign Points in race #" + race.m_raceNo + "\n");

  // Load handicap factor points for track, surface and distance
  Properties prop = null;
  String race_surface = race.m_surface;
  String race_distance = "";
  if (race.m_distance > 1759)
   race_distance = "RT";
  else
   race_distance = "SP";
  int cnt = 0;
  for (Enumeration c = Truline.hf.elements(); c.hasMoreElements();) {
   prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   String surface = prop.getProperty("SURFACE");
   String distance = prop.getProperty("DISTANCE");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   if (track.equals(race.m_track) && surface.equals(race_surface) && distance.equals(race_distance)) {
    cnt++;
    break;
   }
  }

  int EPS1, EN1, FS1, TT1, SS1, CS1, FT1, AS1, RE1, QP1, wPts = 0;
  if (cnt == 0) {
    Log.print("   No track-specific handicap values for track, surface and distance "
        + race.m_track + "/" + race_surface + "/" + race_distance + "\n");
    EPS1 = 8;
    EN1 = 4;
    FS1 = 4;
    TT1 = 4;
    SS1 = 4;
    CS1 = 4;
    FT1 = 4;
    AS1 = 8;
    RE1 = 0;
    QP1 = 8;
  } else {
   EPS1 = Lib.atoi(prop.getProperty("EPS"));
   EN1 = Lib.atoi(prop.getProperty("EN"));
   FS1 = Lib.atoi(prop.getProperty("FS"));
   TT1 = Lib.atoi(prop.getProperty("TT"));
   SS1 = Lib.atoi(prop.getProperty("SS"));
   CS1 = Lib.atoi(prop.getProperty("CS"));
   FT1 = Lib.atoi(prop.getProperty("FT"));
   AS1 = Lib.atoi(prop.getProperty("AS"));
   RE1 = Lib.atoi(prop.getProperty("RE"));
   QP1 = Lib.atoi(prop.getProperty("QP"));
  }

  // Check each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String scratch = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || scratch.equals("S"))
    continue; // position is empty or scratched

   // Check for power trainer
   // if (Truline.userProps.getProperty("Experimental", "N").equals("Y")) {
    int ptPoints = identifyPowerTrainers(race, post);
    if (ptPoints > 0) {
     post.m_handicap.points += ptPoints;
     if (Log.isDebug(Log.TRACE))
      Log.print("     Post# " + post.m_postPosition + ", Horse: "
        + post.m_horseName + " " + ptPoints
        + " points for power trainer\n");
    }

    // Set Track-Specific Trainer/Jockey Percentages
    if (Truline.userProps.getProperty("TL2014", "No").equals("Yes")) {
     String trnJkyPct = setTrainerJockeyPercents(race, post);
     post.m_trnJkyPct = trnJkyPct;
    }

  // }
  // else {
  //  Properties trainerData = Truline.pt.get("NAME", post.m_trainerName);
  //  post.m_trainerNamePT = "  ";
  //  Log.print("     Post# " + post.m_postPosition + ", Horse: "
  //    + post.m_horseName + " for trainer " + post.m_trainerName + " "
  //    + trainerData + "\n");
  //  if (trainerData != null) {
  //   String trainerPointsStr = trainerData.getProperty("POINTS");
  //   if (trainerPointsStr != null) {
  //    post.m_handicap.points += Lib.atoi(trainerPointsStr);
  //    post.m_trainerNamePT = " #";
  //    if (Log.isDebug(Log.TRACE))
  //     Log.print("     Post# " + post.m_postPosition + ", Horse: "
  //       + post.m_horseName + " " + trainerPointsStr
  //       + " points for power trainer\n");
  //   }
  //  }
  // }

   for (int i = 0; i < names.length; i++) {
    if (i == EPS) {
     if (post.m_handicap.rank[i] == 1) {
      post.m_handicap.points += EPS1;
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + EPS1 + " points for #1 " + names[i] + "\n");
      if (post.m_handicap.value[FT] == 0) {
       post.m_handicap.points += 6;
       Properties trainerData = Truline.pt.get("NAME", post.m_trainerName);
       if (trainerData != null)
        post.m_handicap.points += 6;
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + " 6 points for EPS1 and no running line" + "\n");
      }
     } else if (post.m_handicap.rank[i] == 2) {
      post.m_handicap.points += Math.round(EPS1 / 2);
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + Math.round(EPS1 / 2) + " points for #2 " + names[i] + "\n");
      if (post.m_handicap.value[FT] == 0) {
       post.m_handicap.points += 6;
       Properties trainerData = Truline.pt.get("NAME", post.m_trainerName);
       if (trainerData != null)
        post.m_handicap.points += 6;
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + " 6 points for EPS2 and no running line" + "\n");
      }
     } else if (post.m_handicap.rank[i] == 3) {
      post.m_handicap.points += Math.round(EPS1 / 4);
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + Math.round(EPS1 / 4) + " points for #3 " + names[i] + "\n");
      if (post.m_handicap.value[FT] == 0) {
       post.m_handicap.points += 6;
       Properties trainerData = Truline.pt.get("NAME", post.m_trainerName);
       if (trainerData != null)
        post.m_handicap.points += 6;
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + " 6 points for EPS3 and no running line" + "\n");
      }
     }
    } else {
     if (race.m_ignoreRunLine.equals("Y") && i < QP)
      continue;
     if (post.m_handicap.rank[i] == 1) {
      switch (i) {
       case EN:
        wPts = EN1;
        break;
       case FS:
        wPts = FS1;
        break;
       case TT:
        wPts = TT1;
        break;
       case SS:
        wPts = SS1;
        break;
       case CS:
        wPts = CS1;
        break;
       case FT:
        wPts = FT1;
        break;
       case AS:
        wPts = AS1;
        break;
       case RE:
        wPts = RE1;
        break;
       case QP:
        wPts = QP1;
        break;
      }
      post.m_handicap.points += wPts;
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + wPts + " for #1 " + names[i] + "\n");
     } else if (post.m_handicap.rank[i] == 2) {
      post.m_handicap.points += Math.round(wPts / 2);
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + Math.round(wPts / 2) + " points for #2 " + names[i] + "\n");
     } else if (post.m_handicap.rank[i] == 3) {
      post.m_handicap.points += Math.round(wPts / 4);
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + Math.round(wPts / 2) + " points for #3 " + names[i] + "\n");
     }
    }
   }
   // Assign 4 points for must-bet EN
   if (post.m_handicap.extraFlg) {
    post.m_handicap.points += 4;
    if (Log.isDebug(Log.TRACE))
     Log.print("     Post# " + post.m_postPosition + ", Horse: "
       + post.m_horseName + " 4 points for energy over 4.6\n");
   }
   // Check for Owner / Trainer connection
   post.m_ownerTrn = " ";
   String trainer1 = post.m_props.getProperty("TRAINER", "$").toUpperCase();
   int trainerI = trainer1.indexOf(" ");
   String trainerLast = trainer1;
   if (trainerI > 1)
    trainerLast = trainer1.substring(0, trainerI);
   String owner1 = post.m_props.getProperty("OWNER", "$").toUpperCase();
   if (trainer1.equals("$") || (owner1.equals("$")))
    post.m_ownerTrn = " ";
   else if (owner1.indexOf(trainerLast) != -1) {
    post.m_ownerTrn = "*";
    race.m_cnttrnown++;
   }
   // Check for turf sire when surface is turf
   post.m_sireTS = " ";
   post.m_sireTS2 = " ";
   if (race.m_surface.equals("T")) {
    // Check if sire is in top turf sires and add points
    Properties sireData = Truline.ts.get("NAME", post.m_sireName);
    Log.print("     Post# " + post.m_postPosition + ", Horse: "
      + post.m_horseName + " for sire " + post.m_sireName + " " + sireData
      + "\n");
    if (sireData != null) {
     String sirePointsStr = sireData.getProperty("POINTS");
     if (sirePointsStr != null) {
      int sirePoints = Math.round(Lib.atoi(sirePointsStr));
      // Damsire as sire gets sire-level points
      switch (sirePoints) {
       case 2:
        sirePoints = 3;
        break;
       case 4:
        sirePoints = 6;
        break;
       case 6:
        sirePoints = 9;
        break;
      }
      // Add in sire points only when option is "Yes"
      if (Truline.userProps.getProperty("SirePoints", "Y").equals("Y")) {
       post.m_handicap.points += sirePoints;
      }
      post.m_sireTS = "$";
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + " " + sirePoints + " points for turf sire\n");
     }
    }
    // Check if dam sire is in top turf sires and add points
    Properties damSireData = Truline.ts.get("NAME", post.m_damSireName);
    Log.print("     Post# " + post.m_postPosition + ", Horse: "
      + post.m_horseName + " for dam sire " + post.m_damSireName + " "
      + damSireData + "\n");
    if (damSireData != null) {
     String sirePointsStr = damSireData.getProperty("POINTS");
     if (sirePointsStr != null) {
      int sirePoints = Math.round(Lib.atoi(sirePointsStr));
      if (post.m_sireTS != "$") {
       switch (sirePoints) {
        case 2:
         sirePoints = 1;
         break;
        case 4:
         sirePoints = 2;
         break;
        case 6:
         sirePoints = 3;
         break;
       }
      }
      // Add in sire points only when option is "Yes"
      if (Truline.userProps.getProperty("SirePoints", "Y").equals("Y")) {
       post.m_handicap.points += sirePoints;
      }
      post.m_sireTS2 = "d";
      if (post.m_sireTS.equals("$"))
       race.m_cnt$d++;
      if (Log.isDebug(Log.TRACE))
       Log.print("     Post# " + post.m_postPosition + ", Horse: "
         + post.m_horseName + " " + sirePoints
         + " points for dam's turf sire\n");
     }
    }
    /******
     * // Check if sire's sire is in top turf sires and add 1/2 points
     * Properties sireSireData = Truline.ts.get("NAME", post.m_sireSireName);
     * Log.print("     Post# "+post.m_postPosition+", Horse: "+post.m_horseName+
     * " for sire's sire "+post.m_sireSireName+" "+sireSireData+"\n"); if
     * (sireSireData != null) { String sirePointsStr =
     * sireSireData.getProperty("POINTS"); if (sirePointsStr != null) { int
     * sirePoints = Math.round(Lib.atoi(sirePointsStr)/2);
     * post.m_handicap.points += sirePoints; if (post.m_sireTS2.equals(" "))
     * post.m_sireTS2 = "*"; if (Log.isDebug(Log.TRACE))
     * Log.print("     Post# "+
     * post.m_postPosition+", Horse: "+post.m_horseName+" "
     * +sirePoints+" points for sire's turf sire\n"); } }
     */
   }
  }
 }
 /*******************************
  * Assign Bonus Points:
  *
  * Right now, there is a Correlation Table with I think 8 Corelations. Each one
  * is getting 4 points. These are EPS1/EN1, EPS1/CS1, EPS1/AS1/RE1, EPS! and
  * any three other #1 rankings, and EPS1/QP1. Also CS1 and any three other #1
  * rankings, RE1 and any three other #1 rankings, and "any six #1 rankings".
  *
  * If we ever get to quesrying, there are 1025 permutations to check.
  *
  * Once all points are assigned, they are totalled for each horse. The final
  * ranking of the horses gives #1 to the horse with most points, #2 to the next
  * highest total, etc. Disregard the morning line that is presently generated.
  * This will be changed.
  *
  * @param race
  *         - The race being handicapped.
  * @return list of Posts in order by bonus point rankings.
  */
 private static Vector assignBonusPoints(Race race)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Assign Corrolation Bonus Points in race #" + race.m_raceNo
     + "\n");
  Vector bonusRank = new Vector();
  Vector corrolation = new Vector();
  String race_surface = race.m_surface;
  String race_distance = "";
  if (race.m_distance > 1759)
   race_distance = "RT";
  else
   race_distance = "SP";
  String corrVersion = Truline.userProps.getProperty("CorrVersion",
    "ORIG");
  String corrVersion1 = "";
  int cnt = 0;
  for (Enumeration c = Truline.co.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   String surface = prop.getProperty("SURFACE");
   String distance = prop.getProperty("DISTANCE");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   if (track.equals(race.m_track) && surface.equals(race_surface) && distance.equals(race_distance)) {
    corrolation.addElement(prop);
    cnt++;
   }
  }
  if (cnt == 0) {
    Log.print("   No track-specific Correlations for track "
        + race.m_track + "\n");
  }
  if (Log.isDebug(Log.TRACE))
   Log.print("   loaded " + cnt + " Correlations for track " + race.m_track
     + "\n");
  // Check each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String scratch = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || scratch.equals("S"))
    continue; // position is empty or scratched
   // Log.print("     Post# "+post.m_postPosition+", Horse: "+post.m_horseName+"\n");
   int topRanks = 0;
   // Compute total of all Top Ranks for the horse
   for (int i = 0; i < names.length; i++) {
    if (post.m_handicap.rank[i] <= 0)
     continue;
    if (post.m_handicap.rank[i] == 1)
     topRanks++;
   }
   post.m_topRanks = topRanks;
   // Look at each corrolation
   post.m_handicap.bonus = 0;
   for (Enumeration c = corrolation.elements(); c.hasMoreElements();) {
    Properties prop = (Properties) c.nextElement();
    // Parse out the conditions, for example:
    // "ANY=6"
    // "CS=1 ANY=4"
    // "EPS=1 EN=1"
    // "EPS=1 QP=1"
    // "EPS=1 ANY=4"
    // "EPS=1 CS=1"
    // "FS=1 SS=1 QP=1"
    // "RE=1 ANY=4"
    // "EPS=1 AS=1 RE=1"
    String corr = prop.getProperty("CONDITION");
    String items = corr;
    int mark = 0;
    int count = 0;
    while (true) {
     int idx1 = items.indexOf("=");
     if (idx1 < 0)
      break;
     String name = items.substring(0, idx1);
     int idx2 = items.indexOf(" ", idx1);
     if (idx2 == -1)
      idx2 = items.length();
     int val = Lib.atoi(items.substring(idx1 + 1, idx2));
     count++;
     // Log.print("         Corrolation #"+count+"  "+name+"="+val+"\n");
     if (name.equals("ANY")) {
      if (topRanks >= val) {
       // Log.print("          HIT: ANY="+val+"\n");
       mark++;
      }
     } else {
      for (int i = 0; i < names.length; i++) {
       if (post.m_handicap.rank[i] <= 0)
        continue;
       if (name.equals(names[i]) && post.m_handicap.rank[i] == val) {
        // Log.print("          HIT: "+name+"="+val+" value="+post.m_handicap.rank[i]+"\n");
        mark++;
        break;
       }
      }
     }
     if (idx2 >= items.length())
      break;
     items = items.substring(idx2 + 1);
    }
    if (count > 0 && mark == count) {
     // Satisfied all of the elements of the corrolation
     int pnts = Lib.atoi(prop.getProperty("POINTS"));
     post.m_handicap.bonus += pnts;
     if (Log.isDebug(Log.TRACE))
      Log.print("     Post# " + post.m_postPosition + ", Horse: "
        + post.m_horseName + "  " + pnts + " points for " + corr + "\n");
    }
   }

   // Create Bonus Ranks
   int i;
   boolean found = false;
   for (i = 0; i < bonusRank.size(); i++) {
    Post p = (Post) bonusRank.elementAt(i);
    if (p.m_handicap.bonus + p.m_handicap.points < post.m_handicap.bonus
      + post.m_handicap.points) {
     bonusRank.insertElementAt(post, i);
     found = true;
     break;
    }
   }
   if (!found)
    bonusRank.addElement(post);
  }
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Rankings by Total points\n");
  for (int i = 0; i < bonusRank.size(); i++) {
   Post p = (Post) bonusRank.elementAt(i);
   p.m_handicap.bonusRank = i + 1;
   if (i == 0) {
    p.m_pointsAdv = p.m_handicap.bonus + p.m_handicap.points;
    if (i + 1 < bonusRank.size()) {
     Post p2 = (Post) bonusRank.elementAt(i + 1);
     p.m_pointsAdv = p.m_pointsAdv
       - (p2.m_handicap.bonus + p2.m_handicap.points);
    } else
     p.m_pointsAdv = 0;
   }
   if (Log.isDebug(Log.TRACE))
    Log.print("    #" + (i + 1) + " points="
      + (p.m_handicap.bonus + p.m_handicap.points) + " Post# "
      + p.m_postPosition + ", Horse: " + p.m_horseName + "\n");
  }
  return bonusRank;
 }
 /**
  * if it is greater than 8.0F, it is a route.
  */
 public boolean isRoute(Performance p)
 {
  if (p.ppDistance >= F8_0)
   return true;
  return false;
 }
 /**
  * Convert from yards to Furlongs and make a string out of it.
  */
 public static String toF(double d)
 {
  d = d / YdPerF;
  return Lib.ftoa(d, 1) + "F";
 }
 /**
  * Identify any horses that match previous profitable betting correlations
  */
 private static void identifyFlowBets(Race race)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Identify Betting Factors and Flow Bets in race #"
     + race.m_raceNo + "\n");
  String post_betfactors = "";
  Vector betFactors = new Vector();
  String race_surface = race.m_surface;
  String distance = "";
  String surface = "";
  String raceType = "";
  String flowBet1 = "";
  String flowBet = "";
  race.cntHorseFlows = 0;
  String betFactorVersion = Truline.userProps.getProperty("BetFactorVersion",
    "201212");
  String betFactorVersion1 = "";
  int cnt = 0;
  for (Enumeration c = Truline.bf.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   betFactorVersion1 = prop.getProperty("VERSION");
   distance = prop.getProperty("DISTANCE");
   surface = prop.getProperty("SURFACE");
   raceType = prop.getProperty("RACETYPE");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   if (betFactorVersion1.equals(betFactorVersion)) {
    if (track != null && track.equals(race.m_track)) {
     if (distance == null
       || ((distance.equals("RT") && race.m_distance > 1759) || (distance
         .equals("SP") && race.m_distance < 1760))) {
      if (surface == null || surface.equals(race_surface)) {
       if (raceType == null || raceType.equals(race.m_raceType)) {
        betFactors.addElement(prop);
        cnt++;
       }
      }
     }
    }
   }
  }
  /*
   * No standard betting factors if (cnt == 0) { Log.print(
   * "   Loading standard Correlations - no track-specific Correlations for track "
   * +race.m_track+"\n"); for(Enumeration c = Truline.bf.elements();
   * c.hasMoreElements();) { Properties prop = (Properties)c.nextElement();
   * String track = prop.getProperty("TRACK"); distance =
   * prop.getProperty("DISTANCE"); surface = prop.getProperty("SURFACE"); if
   * (track != null && track.equals("XXX")) { if (distance == null ||
   * ((distance.equals("RT") && race.m_distance > 1759) ||
   * (distance.equals("SP") && race.m_distance < 1760))) { if (surface == null
   * || surface.equals(race_surface)) { if (raceType == null ||
   * raceType.equals(race.m_raceType)) { betFactors.addElement(prop); cnt++; } }
   * } } } }
   */
  if (Log.isDebug(Log.TRACE))
   Log.print("   loaded " + cnt + " Betting factors for track " + race.m_track
     + "\n");
  if (cnt == 0)
   return;
  // Check each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String scratch = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || scratch.equals("S"))
    continue; // position is empty or scratched
   Log.print("     Post# " + post.m_postPosition + ", Horse: "
     + post.m_horseName + "\n");
   post.m_betfactors = 0;
   post.cntHorseFlows = -1;
   flowBet = "";
   int topRanks = post.m_topRanks;
   // Look at each betting factor
   int cnt_betting_factors = 0;
   for (Enumeration c = betFactors.elements(); c.hasMoreElements();) {
    Properties prop = (Properties) c.nextElement();
    flowBet1 = prop.getProperty("FLOWBET");
    // Parse out the conditions, for example:
    // "ANY=6"
    // "CS=1 ANY=4"
    // "EPS=1 EN=1"
    // "EPS=1 QP=1"
    // "EPS=1 ANY=4"
    // "EPS=1 CS=1"
    // "FS=1 SS=1 QP=1"
    // "RE=1 ANY=4"
    // "EPS=1 AS=1 RE=1"
    String corr = prop.getProperty("CONDITION");
    String items = corr;
    int mark = 0;
    int count = 0;
    while (true) {
     int idx1 = items.indexOf("=");
     if (idx1 < 0)
      break;
     String name = items.substring(0, idx1);
     int idx2 = items.indexOf(" ", idx1);
     if (idx2 == -1)
      idx2 = items.length();
     int val = Lib.atoi(items.substring(idx1 + 1, idx2));
     count++;
     Log.print("         Corrolation #" + count + "  " + name + "=" + val
       + "\n");
     if (name.equals("ANY")) {
      if (topRanks >= val) {
       Log.print("          HIT: ANY=" + val + "\n");
       mark++;
      }
     } else if (name.equals("TL")) {
      if (post.m_handicap.bonusRank <= val) {
       Log.print("          HIT: TL=" + val + "\n");
       mark++;
      }
     } else if (name.equals("TO")) {
      if (post.m_ownerTrn.equals("*")) {
       Log.print("          HIT: TO=" + val + "\n");
       mark++;
      }
     } else if (name.equals("SD")) {
      if (post.m_sireTS.equals("$") && post.m_sireTS2.equals("d")) {
       Log.print("          HIT: SD=" + val + "\n");
       mark++;
      }
     } else if (name.equals("ONESD")) {
      if (race.m_cnt$d == 1 && post.m_sireTS.equals("$")
        && post.m_sireTS2.equals("d")) {
       Log.print("          HIT: ONESD=" + val + "\n");
       mark++;
      }
     } else {
      for (int i = 0; i < names.length; i++) {
       int rankW = post.m_handicap.rank[i];
       if (rankW <= 0)
        continue;
       if (name.equals(names[i]) && (rankW == val || (val > 1 && rankW <= val))) {
        Log.print("          HIT: " + name + "=" + val + " value="
          + post.m_handicap.rank[i] + "\n");
        mark++;
        break;
       }
      }
     }
     if (idx2 >= items.length())
      break;
     items = items.substring(idx2 + 1);
    }
    if (count > 0 && mark == count) {
     // Satisfied all of the elements of the betting factor
     if (flowBet1.equals("N")) {
      post.m_betfactors++;
     } else if (flowBet1.equals("Y")) {
      flowBet = "*";
      post.m_betfactors++;
     } else if (flowBet1.equals("F")) {
      race.cntHorseFlows++;
      if (post.cntHorseFlows < 9) {
       post.cntHorseFlows++;
       post.horseFlows[post.cntHorseFlows] = corr;
      }
     }
     if (Log.isDebug(Log.TRACE))
      Log.print("     Post# " + post.m_postPosition + ", Horse: "
        + post.m_horseName + " met Betting Factor " + corr + "\n");
    }
   }
   if (post.m_betfactors > 0)
    post.m_betfactorsPR = "b" + Lib.ftoa((int) post.m_betfactors, 0) + flowBet;
  }
  return;
 }
 /**
  * Identify any horses that match previous profitable betting correlations
  */
 private static int identifyPowerTrainers(Race race, Post post)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Identify New Power Trainer File for race " + race.m_raceNo + "\n");
  post.m_trainerNamePT = "  ";
  String race_surface = race.m_surface;
  String trainer = "";
  String distance = "";
  String surface = "";
  String roi = " ";
  int points = 0;
  for (Enumeration c = Truline.pt.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   trainer = prop.getProperty("TRAINER");
   distance = prop.getProperty("DISTANCE");
   surface = prop.getProperty("SURFACE");
   // roi = prop.getProperty("ROI");
   int pointsW = Lib.atoi(prop.getProperty("POINTS"));
   if (post.m_trainerName.equals(trainer)) {
    if (distance == null) {
     if (surface == null) {
      roi = "*";
      post.m_trainerNamePT = roi + "#";
      points += pointsW;
     }
    }
    if (distance != null) {
     if (((distance.equals("RT") && race.m_distance > 1759) || (distance
         .equals("SP") && race.m_distance < 1760))) {
      if (surface.equals(race_surface)) {
       post.m_trainerNamePT = roi + "#";
       points += pointsW;
      }
     }
    }
   }
  }
  /*
   * No power trainers for this surface / distance if (cnt == 0)
   */
   if (points == 0){
    if (Log.isDebug(Log.TRACE))
     Log.print("Did not find power trainers for horse " + post.m_horseName + " in race " + race.m_raceNo
       + "\n");
    return 0;
   }
   else {
    if (Log.isDebug(Log.TRACE))
     Log.print("Found power trainer for horse " + post.m_horseName + " in race " + race.m_raceNo
      + "\n");
    return points;
   }
 }
 /**
  * Identify any horses that match previous profitable betting correlations for trainers
  */
 private static void identifyTrnFlowBets(Race race)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Identify Trainer Flow Bets in race #" + race.m_raceNo + "\n");
  String post_trnfactors = "";
  Vector trnFactors = new Vector();
  String race_surface = race.m_surface;
  String trainer = "";
  String distance = "";
  String surface = "";
  String raceType = "";
  String age = "";
  String sex = "";
  String trnOwn = "";
  String flowBet1 = "";
  String flowBet = "";
  int cnt = 0;
  for (Enumeration c = Truline.tf.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   trainer = prop.getProperty("TRAINER");
   distance = prop.getProperty("DISTANCE");
   surface = prop.getProperty("SURFACE");
   raceType = prop.getProperty("RACETYPE");
   age = prop.getProperty("AGE");
   sex = prop.getProperty("SEX");
   trnOwn = prop.getProperty("TO");
   String sexAge = race.m_props.getProperty("AGESEX", "");
   if (track != null)
    if (track.substring(2).equals("X"))
     track = track.substring(0, 2);
   if (track == null | (track != null && track.equals(race.m_track))) {
    if (distance == null
      || ((distance.equals("RT") && race.m_distance > 1759) || (distance
        .equals("SP") && race.m_distance < 1760))) {
     if (surface == null || surface.equals(race_surface)) {
      if ((raceType == null || raceType.equals(race.m_raceType))
        || (raceType.equals("G") && race.m_purse >= 100000)
        || (raceType.equals("A") && race.m_purse < 100000 && (!race.m_raceType
          .equals("M") && !race.m_raceType.equals("S") && !race.m_raceType
           .equals("C")))) {
       if (age == null || (age.equals("2") && sexAge.charAt(0) == 'A')) {
        if (sex == null
          || (sex.equals("F") && (sexAge.charAt(2) == 'F' || sexAge.charAt(2) == 'M'))
          || (sex.equals("M") && sexAge.charAt(2) != 'F' && sexAge.charAt(2) != 'M')) {
         trnFactors.addElement(prop);
         cnt++;
        }
       }
      }
     }
    }
   }
  }
  /*
   * No trainer factors across all tracks if (cnt == 0) { Log.print(
   * "   Loading standard Correlations - no track-specific Correlations for track "
   * +race.m_track+"\n"); for(Enumeration c = Truline.tf.elements();
   * c.hasMoreElements();) { Properties prop = (Properties)c.nextElement();
   * String track = prop.getProperty("TRACK"); distance =
   * prop.getProperty("DISTANCE"); surface = prop.getProperty("SURFACE"); if
   * (track != null && track.equals("XXX")) { if (distance == null ||
   * ((distance.equals("RT") && race.m_distance > 1759) ||
   * (distance.equals("SP") && race.m_distance < 1760))) { if (surface == null
   * || surface.equals(race_surface)) { if (raceType == null ||
   * raceType.equals(race.m_raceType)) { trnFactors.addElement(prop); cnt++; } }
   * } } } }
   */
  if (Log.isDebug(Log.TRACE))
   Log.print("   loaded " + cnt + " trainer factors for track " + race.m_track
     + "\n");
  if (cnt == 0)
   return;
  // Check each horse in the race.
  for (Enumeration e = race.m_posts.elements(); e.hasMoreElements();) {
   Post post = (Post) e.nextElement();
   String scratch = post.m_props.getProperty("ENTRY", "");
   if (post.m_horseName == null || scratch.equals("S"))
    continue; // position is empty or scratched
   Log.print("     Post# " + post.m_postPosition + ", Horse: "
     + post.m_horseName + "\n");
   post.m_trnfactors = 0;
   // Look at each trainer factor
   int cnt_trainer_factors = 0;
   for (Enumeration c = trnFactors.elements(); c.hasMoreElements();) {
    Properties prop = (Properties) c.nextElement();
    flowBet1 = prop.getProperty("FLOWBET");
    trnOwn = prop.getProperty("TO");
    trainer = prop.getProperty("TRAINER");
    if (trainer.equals(post.m_trainerName)
      && (trnOwn == null | post.m_ownerTrn.equals("*"))) {
     if (flowBet1.equals("N")) {
      post.m_trnfactors++;
     } else if (flowBet1.equals("Y")) {
      flowBet = "*";
      post.m_trnfactors++;
     } else if (flowBet1.equals("F")) {
      post.m_trnfactors++;
      race.cntHorseFlows++;
      if (post.cntHorseFlows < 9) {
       post.cntHorseFlows++;
       post.horseFlows[post.cntHorseFlows] = trnOwn;
      }
     }
    }
    /*
     * No conditions - only check number of times trainer name appears String
     * corr = prop.getProperty("CONDITION"); String items = corr; int mark = 0;
     * int count = 0; while (true) { int idx1 = items.indexOf("="); if (idx1 <
     * 0) break; String name = items.substring(0,idx1); int idx2 =
     * items.indexOf(" ", idx1); if (idx2 == -1) idx2 = items.length(); int val
     * = Lib.atoi(items.substring(idx1+1, idx2)); count++;
     * Log.print("         Corrolation #"+count+"  "+name+"="+val+"\n"); if
     * (name.equals("ANY")) { if (topRanks >= val) {
     * //Log.print("          HIT: ANY="+val+"\n"); mark++; break; } } else if
     * (name.equals("TO")) { if (post.m_ownerTrn.equals("*")) {
     * //Log.print("          HIT: TO="+val+"\n"); mark++; break; } } else if
     * (name.equals("SD")) { if (post.m_sireTS.equals("$") &&
     * post.m_sireTS2.equals("d")) { //Log.print("          HIT: SD="+val+"\n");
     * mark++; break; } } else if (name.equals("ONESD")) { if (race.m_cnt$d == 1
     * && post.m_sireTS.equals("$") && post.m_sireTS2.equals("d")) {
     * //Log.print("          HIT: ONESD="+val+"\n"); mark++; break; } } else {
     * for (int i=0; i < names.length; i++) { int rankW =
     * post.m_handicap.rank[i]; if (rankW <= 0) continue; if
     * (name.equals(names[i]) && (rankW == val || (val > 1 && rankW <= val))) {
     * Log
     * .print("          HIT: "+name+"="+val+" value="+post.m_handicap.rank[i]
     * +"\n"); mark++; break; } } } if (idx2 >= items.length()) break; items =
     * items.substring(idx2+1); } if (count > 0 && mark == count) { // Satisfied
     * all of the elements of the trainer factor post.m_trnfactors++; if
     * (Log.isDebug(Log.TRACE))
     * Log.print("     Post# "+post.m_postPosition+", Horse: "+post.m_horseName
     * +" met "+corr+"\n"); }
     */
   }
   if (post.m_trnfactors > 0)
    post.m_trnfactorsPR = "t" + Lib.ftoa((int) post.m_trnfactors, 0) + flowBet;
  }
  return;
 }
 /**
  * Identify any horses that match previous profitable betting correlations for trainers
  */
 private static String setTrainerJockeyPercents(Race race, Post post)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Set Track-specific Trainer and Jockey Percentages" + race.m_raceNo + "\n");
  String post_trnJkyPct = "TJ0";
  String surfaceDist = "";
  String race_surface = race.m_surface;
  if (race.m_distance > 1759)
   surfaceDist = race.m_surface+"R";
  else
   surfaceDist = race.m_surface+"S";

  // Find Trainer Percentage
  for (Enumeration c = Truline.tt.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   String trainer = prop.getProperty("TRAINER");
   if (track.equals(race.m_track) && trainer.equals(post.m_trainerName)) {
    switch (surfaceDist) {
     case "DS":
      post_trnJkyPct = "TJ"+prop.getProperty("DS");
      break;
     case "DR":
      post_trnJkyPct = "TJ"+prop.getProperty("DR");
      break;
     case "TS":
      post_trnJkyPct = "TJ"+prop.getProperty("TS");
      break;
     case "TR":
      post_trnJkyPct = "TJ"+prop.getProperty("TR");
      break;
    }
   break;
   }
  }

  // Find Jockey Percentage
  boolean foundJky = false;
  for (Enumeration c = Truline.jt.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   String jockey = prop.getProperty("JOCKEY");
   if (track.equals(race.m_track) && jockey.equals(post.m_jockeyName)) {
    switch (surfaceDist) {
     case "DS":
      post_trnJkyPct = post_trnJkyPct+"/"+prop.getProperty("DS");
      break;
     case "DR":
      post_trnJkyPct = post_trnJkyPct+"/"+prop.getProperty("DR");
      break;
     case "TS":
      post_trnJkyPct = post_trnJkyPct+"/"+prop.getProperty("TS");
      break;
     case "TR":
      post_trnJkyPct = post_trnJkyPct+"/"+prop.getProperty("TR");
      break;
    }
   foundJky = true;
   break;
   }
  }
  if (!foundJky)
   post_trnJkyPct = post_trnJkyPct+"/0";

  return post_trnJkyPct;
 }
 /**
  * Identify if the race qualifies as a race flow bet
  */
 private static void identifyRaceFlowBets(Race race)
 {
  if (Log.isDebug(Log.TRACE))
   Log.print("\n  Identify Race Flow Bets in race #" + race.m_raceNo + "\n");
  String race_surface = race.m_surface;
  String distance = "";
  String surface = "";
  String raceType = "";
  String betType = "";
  String factors = "";
  String raceFlow = "";
  int cnt = 0;
  for (Enumeration c = Truline.rf.elements(); c.hasMoreElements();) {
   Properties prop = (Properties) c.nextElement();
   String track = prop.getProperty("TRACK");
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   distance = prop.getProperty("DISTANCE");
   surface = prop.getProperty("SURFACE");
   raceType = prop.getProperty("RACETYPE");
   betType = prop.getProperty("BETTYPE");
   factors = prop.getProperty("FACTORS");
   raceFlow = betType + "  " + factors;
   if (track.substring(2).equals("X"))
    track = track.substring(0, 2);
   if (track != null && track.equals(race.m_track)) {
    if (distance == null
      || ((distance.equals("RT") && race.m_distance > 1759) || (distance
        .equals("SP") && race.m_distance < 1760))) {
     if (surface == null || surface.equals(race_surface)) {
      if ((raceType == null || raceType.equals(race.m_raceType))
        || (raceType.equals("G") && race.m_purse >= 100000)
        || (raceType.equals("A") && race.m_purse < 100000 && (!race.m_raceType
          .equals("M") && !race.m_raceType.equals("S") && !race.m_raceType
           .equals("C")))) {
       if (race.cntRaceFlows < 4 && race.m_cnthorses > 6 && race.m_cntnrl < 4
         && race.m_cntnrlML == 0) {
        race.cntRaceFlows++;
        race.raceFlows[race.cntRaceFlows] = raceFlow;
       }
      }
     }
    }
   }
  }
  return;
 }
}
