/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nasa.jpl.xdata.nba.impoexpo.parse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.GameInfo;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.GameStats;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.GameSummary;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.InactivePlayers;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.LastMeeting;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.LineScore;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.Notebook;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.Officials;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.OtherStats;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.PlayerStats;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.Preview;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.Recap;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.SeasonSeries;
import gov.nasa.jpl.xdata.nba.impoexpo.structs.TeamStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * ParseUtil, amongst other things, contains static functions for
 * implementing dataset specific parse functions involved within
 * ETL.
 */
public class ParseUtil {
  
  private static Logger LOG = LoggerFactory.getLogger(ParseUtil.class);

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /**
   * Simply reads in a String path to a Preview file, parses the File, populates
   * a Preview object and returns that object.
   * @param preview 
   * @param string
   * @return
   */
  public static Preview parsePreview(Preview preview, String string) {
    preview.setPreviewText(parseTextFile(string));
    return preview;
    
  }

  /**
   * Simply reads in a String path to a Recap file, parses the File, populates
   * a Recap object and returns that object.
   * @param recap 
   * @param string
   * @return
   */
  public static Recap parseRecap(Recap recap, String string) {
    recap.setRecapText(parseTextFile(string));
    return recap;
  }

  /**
   * Simply reads in a String path to a Notebook file, parses the File, populates
   * a Notebook object and returns that object.
   * TODO the {@link }
   * @param string
   * @return
   * @throws IOException 
   */
  public static Notebook parseNotebook(Notebook notebook, String string) throws IOException {
    LOG.info("Parsing Notebook: {}", string);
    BufferedReader reader = new BufferedReader(new FileReader(string));
    try {
      String line = reader.readLine();
      do {
        if (line.contains("Notebook:")) {
          notebook.setTeamNotebook(line);
        } else if (line.contains("Posted")) {
          notebook.setPostedDate(line);
        } else if (line.contains("THE FACT:")) {
          notebook.setTheFacts(line);
        } else if (line.contains("THE LEAD:")) {
          notebook.setTheLead(line);
        } else if (line.contains("QUOTABLE:") || line.contains("QUOTABLE II:") || line.contains("QUOTABLE III:")) {
          notebook.getTheQuotes().add(line);
        } else if (line.contains("THE STAT")) {
          notebook.setTheStat(line);
        }  else if (line.contains("TURNING POINT:")) {
          notebook.setTheTurningPoint(line);
        } else if (line.contains("HOT:")) {
          notebook.setHot(line);
        } else if (line.contains("NOT:")) {
          notebook.setNot(line);
        } else if (line.contains("GOOD MOVE:")) {
          notebook.setGoodMove(line);
        } else if (line.contains("BAD MOVE:")) {
          notebook.setBadMove(line);
        } else if (line.contains("NEXT:")) {
          notebook.setUpNext(line);
        }
        
        line = reader.readLine();
      } while(line != null);
      
    } finally {
      reader.close();  
    }
    return notebook;
  }

  public static GameStats parseGameStats(GameStats gameStats, String string) throws IOException, ParseException {
    LOG.info("Parsing GameStats: {}", string);
    FileReader reader = new FileReader(string);

    // Pare with JSON simple parser
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

    JSONArray result = (JSONArray) jsonObject.get("resultSets");
    JSONObject jObj = (JSONObject) result.get(0);
    JSONArray rows = (JSONArray) jObj.get("rowset");
    
    //Lewis
    GameSummary summary = parseGameSummary(jsonObject);
    LineScore score = parseLineScore(jsonObject);
    SeasonSeries series = parseSeasonSeries(jsonObject);
    LastMeeting meeting = parseLastMeeting(jsonObject);
    PlayerStats playerStats = parsePlayerStats(jsonObject);
    TeamStats teamStats = parseTeamStats(jsonObject);
    
    //Tyler
    OtherStats otherStats = parseOtherStats(jsonObject);
    Officials officials = parseOfficials(jsonObject);
    GameInfo info = parseInfo(jsonObject);
    InactivePlayers inactivePlayers = parseInactivePlayers(jsonObject);
    AvailableVideo video = parseAvailableVideo(jsonObject);
    
    GameStats stats = GameStats.newBuilder().setGameSummary(summary).setLineScore(score)
        .setSeasonSeries(series).setLastMeeting(meeting).setPlayerStats(playerStats)
        .setTeamStats(teamStats).setOtherStats(otherStats).setOfficials(officials)
        .setGameInfo(info).setInactivePlayers(inactivePlayers).setAvailableVideo(video).build();
    
    return stats;
    
  }

  public static GameSummary parseGameSummary(JSONObject jsonObject) {
    JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
    JSONObject infoObject = (JSONObject) resultSets.get(0);
    JSONArray rowSet = (JSONArray) infoObject.get("rowSet");
    JSONArray row = (JSONArray) rowSet.get(0);
    String gameSummaryDateEst = (String) row.get(0);
    int gameSummarySequence   = Integer.parseInt(row.get(1).toString());
    int gameSummaryId         = Integer.parseInt((String) row.get(2));
    int gameStatusId          = Integer.parseInt(row.get(3).toString());
    String gameStatusText     = (String) row.get(4);
    String gameCode           = (String) row.get(5);
    int homeTeamId            = Integer.parseInt(row.get(6).toString());
    int visitorTeamId         = Integer.parseInt(row.get(7).toString());
    int season                = Integer.parseInt(row.get(8).toString());
    int livePeriod            = Integer.parseInt(row.get(9).toString());
    String livePcTimeString = row.get(10).toString();
    int livePcTime            = livePcTimeString.isEmpty() ? 0 : Integer.parseInt(livePcTimeString);
    String broadcasterAbbrev  = (String) row.get(11);
    String livePeriodTimeBcast= (String) row.get(12);
    int whStatus              = Integer.parseInt(row.get(13).toString());

    return GameSummary.newBuilder()
            .setGameSummaryDateEst(gameSummaryDateEst)
            .setGameSummarySequence(gameSummarySequence)
            .setGameGameSummaryId(gameSummaryId)
            .setGameStatusId(gameStatusId)
            .setGameStatusText(gameStatusText)
            .setGameCode(gameCode)
            .setGameSummaryHomeTeamId(homeTeamId)
            .setGameSummaryVisitorTeamId(visitorTeamId)
            .setSeason(season)
            .setLivePeriod(livePeriod)
            .setLivePcTime(livePcTime)
            .setNatlTvBroadcasterAbbreviation(broadcasterAbbrev)
            .setLivePeriodTimeBcast(livePeriodTimeBcast)
            .setWhStatus(whStatus)
            .build();
  }
  public static LineScore parseLineScore(JSONObject jsonObject) {
    return LineScore.newBuilder().build();
      // TODO: LineScore contains two entries, one for each team.
  }
  public static SeasonSeries parseSeasonSeries(JSONObject jsonObject) {
    JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
    JSONObject infoObject = (JSONObject) resultSets.get(2);
    JSONArray rowSet = (JSONArray) infoObject.get("rowSet");
    JSONArray row = (JSONArray) rowSet.get(0);
    int seriesId = Integer.parseInt(row.get(0).toString());
    int homeTeamId = Integer.parseInt(row.get(1).toString());
    int visitorTeamId = Integer.parseInt(row.get(2).toString());
    String seriesDateEst = (String) row.get(3);
    int homeTeamWins = Integer.parseInt(row.get(4).toString());
    int homeTeamLosses = Integer.parseInt(row.get(5).toString());
    String seriesLeader = (String) row.get(6);
    return SeasonSeries.newBuilder()
            .setGameSeasonSeriesId(seriesId)
            .setSeasonSeriesHomeTeamId(homeTeamId)
            .setSeasonSeriesVisitorTeamId(visitorTeamId)
            .setGameSeasonSeriesDateEst(seriesDateEst)
            .setHomeTeamWins(homeTeamWins)
            .setHomeTeamLosses(homeTeamLosses)
            .setSeriesLeader(seriesLeader)
            .build();
  }
  public static LastMeeting parseLastMeeting(JSONObject jsonObject) {
    JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
    JSONObject infoObject = (JSONObject) resultSets.get(3);
    JSONArray rowSet = (JSONArray) infoObject.get("rowSet");
    JSONArray row = (JSONArray) rowSet.get(0);
    int gameLastMeetingId = Integer.parseInt(row.get(0).toString());
    int lastGameId = Integer.parseInt(row.get(1).toString());
    String lastGameDate = (String) row.get(2);
    int lastGameHomeTeamId = Integer.parseInt(row.get(3).toString());
    String lastGameHomeTeamCity = (String) row.get(4);
    String lastGameHomeTeamName = (String) row.get(5);
    String lastGameHomeTeamAbbreviation = (String) row.get(6);
    int lastGameHomeTeamPoints = Integer.parseInt(row.get(7).toString());
    int lastGameVisitorId = Integer.parseInt(row.get(8).toString());
    String lastGameVisitorCity = (String) row.get(9);
    String lastGameVisitorName = (String) row.get(10);
    String lastGameVisitorCity1 = (String) row.get(11);
    int lastGameVisitorPoints = Integer.parseInt(row.get(12).toString());
    return LastMeeting.newBuilder()
            .setGameLastMeetingId(gameLastMeetingId)
            .setLastGameId(lastGameId)
            .setLastGameDateEst(lastGameDate)
            .setLastGameHomeTeamId(lastGameHomeTeamId)
            .setLastGameHomeTeamCity(lastGameHomeTeamCity)
            .setLastGameHomeTeamName(lastGameHomeTeamName)
            .setLastGameHomeTeamAbbreviation(lastGameHomeTeamAbbreviation)
            .setLastGameHomeTeamPoints(lastGameHomeTeamPoints)
            .setLastGameHomeTeamId(lastGameHomeTeamId)
            .setLastGameVisitorTeamId(lastGameVisitorId)
            .setLastGameVisitorTeamCity(lastGameVisitorCity)
            .setLastGameVisitorTeamName(lastGameVisitorName)
            .setLastGameVisitorTeamCity1(lastGameVisitorCity1)
            .setLastGameVisitorTeamPoints(lastGameVisitorPoints)
            .build();
  }
  public static PlayerStats parsePlayerStats(JSONObject jsonObject) {
    return PlayerStats.newBuilder()
            // TODO: PlayerStats has many players, each with stats
            // which possibly have null values.
            .build();
  }
  public static TeamStats parseTeamStats(JSONObject jsonObject) {
    return TeamStats.newBuilder()
            // TODO: TeamStats has two teams, each with stats.
            .build();
  }

  public static OtherStats parseOtherStats(JSONObject jsonObject) {
/*      JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
      JSONObject result = (JSONObject) resultSets.get(6);
      assert("OtherStats".equals(result.get("name")));
      int largestLead = ;
      int leadChanges = ;
      int leagueId = ;*/

      OtherStats otherStats = OtherStats.newBuilder()
              /*.setLeagueId()
              .setSeasonId()
              .setOtherStatsTeamId()
              .setLargestLead()
              .setLeadChanges()
              .setOtherStatsTeamAbbreviation()
              .setOtherStatsTeamCity()
              .setPts2ndChance()
              .setPtsFb()
              .setPtsPaint()
              .setTimesTied()*/
              .build();
      return otherStats;
  }

  public static Officials parseOfficials(JSONObject jsonObject) {
      JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");

      Officials officials = Officials.newBuilder()
              .build();
      return officials;
  }

  public static GameInfo parseInfo(JSONObject jsonObject) {
      JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
      JSONObject infoObject = (JSONObject) resultSets.get(8);
      JSONArray rowSet = (JSONArray) infoObject.get("rowSet");
      JSONArray row = (JSONArray) rowSet.get(0);
      String gameDate = (String) row.get(0);
      int attendance = Integer.parseInt(row.get(1).toString());
      String gameTime = (String) row.get(2);
      return GameInfo.newBuilder()
              .setGameDate(gameDate)
              .setAttendance(attendance)
              .setGameTime(gameTime)
              .build();
  }

  public static InactivePlayers parseInactivePlayers(JSONObject jsonObject) {
      JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
      JSONObject inactivePlayersObject = (JSONObject) resultSets.get(9);
      JSONArray rowSet = (JSONArray) inactivePlayersObject.get("rowSet");
      InactivePlayers.Builder builder = InactivePlayers.newBuilder();
      return builder.build();
      /*for (Object o : rowSet) {
          JSONArray player = (JSONArray) o;
          builder.set
      }*/
  }

  public static AvailableVideo parseAvailableVideo(JSONObject jsonObject) {
      JSONArray resultSets = (JSONArray) jsonObject.get("resultSets");
      JSONObject availableVideoObject = (JSONObject) resultSets.get(10);
      JSONArray rowSet = (JSONArray) availableVideoObject.get("rowSet");
      JSONArray row = (JSONArray) rowSet.get(0);
      return AvailableVideo.newBuilder()
              .setVideoAvailableFlag(Integer.parseInt(row.get(0).toString()))
              .build();
  }
  

  private static GameSummary parseGameSummary() {
    // TODO Auto-generated method stub
    return null;
  }

  private static String parseTextFile(String textString) {
    LOG.info("Parsing Text: {}", textString);
    InputStream is = null;
    try {
      is = new FileInputStream(textString);
    } catch (Exception e) {
      // TODO: handle exception
    }
    Tika tika = new Tika();
    String text = null;
    try {
      text = tika.parseToString(is);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TikaException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      try {
        is.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return text;
  }

}
