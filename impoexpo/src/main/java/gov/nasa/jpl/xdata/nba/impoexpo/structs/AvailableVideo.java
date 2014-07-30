/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package gov.nasa.jpl.xdata.nba.impoexpo.structs;  
@SuppressWarnings("all")
public class AvailableVideo extends org.apache.gora.persistency.impl.PersistentBase implements org.apache.avro.specific.SpecificRecord, org.apache.gora.persistency.Persistent {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvailableVideo\",\"namespace\":\"gov.nasa.jpl.xdata.nba.impoexpo.structs\",\"fields\":[{\"name\":\"__g__dirty\",\"type\":\"bytes\",\"doc\":\"Bytes used to represent weather or not a field is dirty.\",\"default\":\"AA==\"},{\"name\":\"videoAvailableFlag\",\"type\":\"long\",\"default\":0}]}");

  /** Enum containing all data bean's fields. */
  public static enum Field {
    __G__DIRTY(0, "__g__dirty"),
    VIDEO_AVAILABLE_FLAG(1, "videoAvailableFlag"),
    ;
    /**
     * Field's index.
     */
    private int index;

    /**
     * Field's name.
     */
    private String name;

    /**
     * Field's constructor
     * @param index field's index.
     * @param name field's name.
     */
    Field(int index, String name) {this.index=index;this.name=name;}

    /**
     * Gets field's index.
     * @return int field's index.
     */
    public int getIndex() {return index;}

    /**
     * Gets field's name.
     * @return String field's name.
     */
    public String getName() {return name;}

    /**
     * Gets field's attributes to string.
     * @return String field's attributes to string.
     */
    public String toString() {return name;}
  };

  public static final String[] _ALL_FIELDS = {
  "__g__dirty",
  "videoAvailableFlag",
  };

  /** Bytes used to represent weather or not a field is dirty. */
  private java.nio.ByteBuffer __g__dirty = java.nio.ByteBuffer.wrap(new byte[1]);
  private long videoAvailableFlag;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return __g__dirty;
    case 1: return videoAvailableFlag;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value) {
    switch (field$) {
    case 0: __g__dirty = (java.nio.ByteBuffer)(value); break;
    case 1: videoAvailableFlag = (java.lang.Long)(value); break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'videoAvailableFlag' field.
   */
  public java.lang.Long getVideoAvailableFlag() {
    return videoAvailableFlag;
  }

  /**
   * Sets the value of the 'videoAvailableFlag' field.
   * @param value the value to set.
   */
  public void setVideoAvailableFlag(java.lang.Long value) {
    this.videoAvailableFlag = value;
    setDirty(1);
  }
  
  /**
   * Checks the dirty status of the 'videoAvailableFlag' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isVideoAvailableFlagDirty(java.lang.Long value) {
    return isDirty(1);
  }

  /** Creates a new AvailableVideo RecordBuilder */
  public static gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder newBuilder() {
    return new gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder();
  }
  
  /** Creates a new AvailableVideo RecordBuilder by copying an existing Builder */
  public static gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder newBuilder(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder other) {
    return new gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder(other);
  }
  
  /** Creates a new AvailableVideo RecordBuilder by copying an existing AvailableVideo instance */
  public static gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder newBuilder(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo other) {
    return new gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder(other);
  }
  
  private static java.nio.ByteBuffer deepCopyToReadOnlyBuffer(
      java.nio.ByteBuffer input) {
    java.nio.ByteBuffer copy = java.nio.ByteBuffer.allocate(input.capacity());
    int position = input.position();
    input.reset();
    int mark = input.position();
    int limit = input.limit();
    input.rewind();
    input.limit(input.capacity());
    copy.put(input);
    input.rewind();
    copy.rewind();
    input.position(mark);
    input.mark();
    copy.position(mark);
    copy.mark();
    input.position(position);
    copy.position(position);
    input.limit(limit);
    copy.limit(limit);
    return copy.asReadOnlyBuffer();
  }
  
  /**
   * RecordBuilder for AvailableVideo instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvailableVideo>
    implements org.apache.avro.data.RecordBuilder<AvailableVideo> {

    private java.nio.ByteBuffer __g__dirty;
    private long videoAvailableFlag;

    /** Creates a new Builder */
    private Builder() {
      super(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing AvailableVideo instance */
    private Builder(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo other) {
            super(gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.SCHEMA$);
      if (isValidValue(fields()[0], other.__g__dirty)) {
        this.__g__dirty = (java.nio.ByteBuffer) data().deepCopy(fields()[0].schema(), other.__g__dirty);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.videoAvailableFlag)) {
        this.videoAvailableFlag = (java.lang.Long) data().deepCopy(fields()[1].schema(), other.videoAvailableFlag);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'videoAvailableFlag' field */
    public java.lang.Long getVideoAvailableFlag() {
      return videoAvailableFlag;
    }
    
    /** Sets the value of the 'videoAvailableFlag' field */
    public gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder setVideoAvailableFlag(long value) {
      validate(fields()[1], value);
      this.videoAvailableFlag = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'videoAvailableFlag' field has been set */
    public boolean hasVideoAvailableFlag() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'videoAvailableFlag' field */
    public gov.nasa.jpl.xdata.nba.impoexpo.structs.AvailableVideo.Builder clearVideoAvailableFlag() {
      fieldSetFlags()[1] = false;
      return this;
    }
    
    @Override
    public AvailableVideo build() {
      try {
        AvailableVideo record = new AvailableVideo();
        record.__g__dirty = fieldSetFlags()[0] ? this.__g__dirty : (java.nio.ByteBuffer) java.nio.ByteBuffer.wrap(new byte[1]);
        record.videoAvailableFlag = fieldSetFlags()[1] ? this.videoAvailableFlag : (java.lang.Long) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
  
  public AvailableVideo.Tombstone getTombstone(){
  	return TOMBSTONE;
  }

  public AvailableVideo newInstance(){
    return newBuilder().build();
  }

  private static final Tombstone TOMBSTONE = new Tombstone();
  
  public static final class Tombstone extends AvailableVideo implements org.apache.gora.persistency.Tombstone {
  
      private Tombstone() { }
  
	  				  /**
	   * Gets the value of the 'videoAvailableFlag' field.
		   */
	  public java.lang.Long getVideoAvailableFlag() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'videoAvailableFlag' field.
		   * @param value the value to set.
	   */
	  public void setVideoAvailableFlag(java.lang.Long value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'videoAvailableFlag' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isVideoAvailableFlagDirty(java.lang.Long value) {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
		  
  }
  
}

