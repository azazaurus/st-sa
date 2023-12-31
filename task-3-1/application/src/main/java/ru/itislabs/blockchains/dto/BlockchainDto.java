// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BlockchainDtos.proto

// Protobuf Java Version: 3.25.1
package ru.itislabs.blockchains.dto;

/**
 * Protobuf type {@code ru.itislabs.blockchains.dto.BlockchainDto}
 */
public final class BlockchainDto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ru.itislabs.blockchains.dto.BlockchainDto)
    BlockchainDtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BlockchainDto.newBuilder() to construct.
  private BlockchainDto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BlockchainDto() {
    chain_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new BlockchainDto();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ru.itislabs.blockchains.dto.BlockchainDtos.internal_static_ru_itislabs_blockchains_dto_BlockchainDto_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ru.itislabs.blockchains.dto.BlockchainDtos.internal_static_ru_itislabs_blockchains_dto_BlockchainDto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ru.itislabs.blockchains.dto.BlockchainDto.class, ru.itislabs.blockchains.dto.BlockchainDto.Builder.class);
  }

  public static final int CHAIN_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<ru.itislabs.blockchains.dto.BlockDto> chain_;
  /**
   * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
   */
  @java.lang.Override
  public java.util.List<ru.itislabs.blockchains.dto.BlockDto> getChainList() {
    return chain_;
  }
  /**
   * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends ru.itislabs.blockchains.dto.BlockDtoOrBuilder> 
      getChainOrBuilderList() {
    return chain_;
  }
  /**
   * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
   */
  @java.lang.Override
  public int getChainCount() {
    return chain_.size();
  }
  /**
   * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
   */
  @java.lang.Override
  public ru.itislabs.blockchains.dto.BlockDto getChain(int index) {
    return chain_.get(index);
  }
  /**
   * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
   */
  @java.lang.Override
  public ru.itislabs.blockchains.dto.BlockDtoOrBuilder getChainOrBuilder(
      int index) {
    return chain_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < chain_.size(); i++) {
      output.writeMessage(1, chain_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < chain_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, chain_.get(i));
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ru.itislabs.blockchains.dto.BlockchainDto)) {
      return super.equals(obj);
    }
    ru.itislabs.blockchains.dto.BlockchainDto other = (ru.itislabs.blockchains.dto.BlockchainDto) obj;

    if (!getChainList()
        .equals(other.getChainList())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getChainCount() > 0) {
      hash = (37 * hash) + CHAIN_FIELD_NUMBER;
      hash = (53 * hash) + getChainList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static ru.itislabs.blockchains.dto.BlockchainDto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static ru.itislabs.blockchains.dto.BlockchainDto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itislabs.blockchains.dto.BlockchainDto parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ru.itislabs.blockchains.dto.BlockchainDto prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ru.itislabs.blockchains.dto.BlockchainDto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ru.itislabs.blockchains.dto.BlockchainDto)
      ru.itislabs.blockchains.dto.BlockchainDtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.itislabs.blockchains.dto.BlockchainDtos.internal_static_ru_itislabs_blockchains_dto_BlockchainDto_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.itislabs.blockchains.dto.BlockchainDtos.internal_static_ru_itislabs_blockchains_dto_BlockchainDto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.itislabs.blockchains.dto.BlockchainDto.class, ru.itislabs.blockchains.dto.BlockchainDto.Builder.class);
    }

    // Construct using ru.itislabs.blockchains.dto.BlockchainDto.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      if (chainBuilder_ == null) {
        chain_ = java.util.Collections.emptyList();
      } else {
        chain_ = null;
        chainBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ru.itislabs.blockchains.dto.BlockchainDtos.internal_static_ru_itislabs_blockchains_dto_BlockchainDto_descriptor;
    }

    @java.lang.Override
    public ru.itislabs.blockchains.dto.BlockchainDto getDefaultInstanceForType() {
      return ru.itislabs.blockchains.dto.BlockchainDto.getDefaultInstance();
    }

    @java.lang.Override
    public ru.itislabs.blockchains.dto.BlockchainDto build() {
      ru.itislabs.blockchains.dto.BlockchainDto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ru.itislabs.blockchains.dto.BlockchainDto buildPartial() {
      ru.itislabs.blockchains.dto.BlockchainDto result = new ru.itislabs.blockchains.dto.BlockchainDto(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(ru.itislabs.blockchains.dto.BlockchainDto result) {
      if (chainBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          chain_ = java.util.Collections.unmodifiableList(chain_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.chain_ = chain_;
      } else {
        result.chain_ = chainBuilder_.build();
      }
    }

    private void buildPartial0(ru.itislabs.blockchains.dto.BlockchainDto result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ru.itislabs.blockchains.dto.BlockchainDto) {
        return mergeFrom((ru.itislabs.blockchains.dto.BlockchainDto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ru.itislabs.blockchains.dto.BlockchainDto other) {
      if (other == ru.itislabs.blockchains.dto.BlockchainDto.getDefaultInstance()) return this;
      if (chainBuilder_ == null) {
        if (!other.chain_.isEmpty()) {
          if (chain_.isEmpty()) {
            chain_ = other.chain_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureChainIsMutable();
            chain_.addAll(other.chain_);
          }
          onChanged();
        }
      } else {
        if (!other.chain_.isEmpty()) {
          if (chainBuilder_.isEmpty()) {
            chainBuilder_.dispose();
            chainBuilder_ = null;
            chain_ = other.chain_;
            bitField0_ = (bitField0_ & ~0x00000001);
            chainBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getChainFieldBuilder() : null;
          } else {
            chainBuilder_.addAllMessages(other.chain_);
          }
        }
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              ru.itislabs.blockchains.dto.BlockDto m =
                  input.readMessage(
                      ru.itislabs.blockchains.dto.BlockDto.parser(),
                      extensionRegistry);
              if (chainBuilder_ == null) {
                ensureChainIsMutable();
                chain_.add(m);
              } else {
                chainBuilder_.addMessage(m);
              }
              break;
            } // case 10
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.util.List<ru.itislabs.blockchains.dto.BlockDto> chain_ =
      java.util.Collections.emptyList();
    private void ensureChainIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        chain_ = new java.util.ArrayList<ru.itislabs.blockchains.dto.BlockDto>(chain_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        ru.itislabs.blockchains.dto.BlockDto, ru.itislabs.blockchains.dto.BlockDto.Builder, ru.itislabs.blockchains.dto.BlockDtoOrBuilder> chainBuilder_;

    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public java.util.List<ru.itislabs.blockchains.dto.BlockDto> getChainList() {
      if (chainBuilder_ == null) {
        return java.util.Collections.unmodifiableList(chain_);
      } else {
        return chainBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public int getChainCount() {
      if (chainBuilder_ == null) {
        return chain_.size();
      } else {
        return chainBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public ru.itislabs.blockchains.dto.BlockDto getChain(int index) {
      if (chainBuilder_ == null) {
        return chain_.get(index);
      } else {
        return chainBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder setChain(
        int index, ru.itislabs.blockchains.dto.BlockDto value) {
      if (chainBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureChainIsMutable();
        chain_.set(index, value);
        onChanged();
      } else {
        chainBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder setChain(
        int index, ru.itislabs.blockchains.dto.BlockDto.Builder builderForValue) {
      if (chainBuilder_ == null) {
        ensureChainIsMutable();
        chain_.set(index, builderForValue.build());
        onChanged();
      } else {
        chainBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder addChain(ru.itislabs.blockchains.dto.BlockDto value) {
      if (chainBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureChainIsMutable();
        chain_.add(value);
        onChanged();
      } else {
        chainBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder addChain(
        int index, ru.itislabs.blockchains.dto.BlockDto value) {
      if (chainBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureChainIsMutable();
        chain_.add(index, value);
        onChanged();
      } else {
        chainBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder addChain(
        ru.itislabs.blockchains.dto.BlockDto.Builder builderForValue) {
      if (chainBuilder_ == null) {
        ensureChainIsMutable();
        chain_.add(builderForValue.build());
        onChanged();
      } else {
        chainBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder addChain(
        int index, ru.itislabs.blockchains.dto.BlockDto.Builder builderForValue) {
      if (chainBuilder_ == null) {
        ensureChainIsMutable();
        chain_.add(index, builderForValue.build());
        onChanged();
      } else {
        chainBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder addAllChain(
        java.lang.Iterable<? extends ru.itislabs.blockchains.dto.BlockDto> values) {
      if (chainBuilder_ == null) {
        ensureChainIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, chain_);
        onChanged();
      } else {
        chainBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder clearChain() {
      if (chainBuilder_ == null) {
        chain_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        chainBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public Builder removeChain(int index) {
      if (chainBuilder_ == null) {
        ensureChainIsMutable();
        chain_.remove(index);
        onChanged();
      } else {
        chainBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public ru.itislabs.blockchains.dto.BlockDto.Builder getChainBuilder(
        int index) {
      return getChainFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public ru.itislabs.blockchains.dto.BlockDtoOrBuilder getChainOrBuilder(
        int index) {
      if (chainBuilder_ == null) {
        return chain_.get(index);  } else {
        return chainBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public java.util.List<? extends ru.itislabs.blockchains.dto.BlockDtoOrBuilder> 
         getChainOrBuilderList() {
      if (chainBuilder_ != null) {
        return chainBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(chain_);
      }
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public ru.itislabs.blockchains.dto.BlockDto.Builder addChainBuilder() {
      return getChainFieldBuilder().addBuilder(
          ru.itislabs.blockchains.dto.BlockDto.getDefaultInstance());
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public ru.itislabs.blockchains.dto.BlockDto.Builder addChainBuilder(
        int index) {
      return getChainFieldBuilder().addBuilder(
          index, ru.itislabs.blockchains.dto.BlockDto.getDefaultInstance());
    }
    /**
     * <code>repeated .ru.itislabs.blockchains.dto.BlockDto chain = 1;</code>
     */
    public java.util.List<ru.itislabs.blockchains.dto.BlockDto.Builder> 
         getChainBuilderList() {
      return getChainFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        ru.itislabs.blockchains.dto.BlockDto, ru.itislabs.blockchains.dto.BlockDto.Builder, ru.itislabs.blockchains.dto.BlockDtoOrBuilder> 
        getChainFieldBuilder() {
      if (chainBuilder_ == null) {
        chainBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            ru.itislabs.blockchains.dto.BlockDto, ru.itislabs.blockchains.dto.BlockDto.Builder, ru.itislabs.blockchains.dto.BlockDtoOrBuilder>(
                chain_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        chain_ = null;
      }
      return chainBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ru.itislabs.blockchains.dto.BlockchainDto)
  }

  // @@protoc_insertion_point(class_scope:ru.itislabs.blockchains.dto.BlockchainDto)
  private static final ru.itislabs.blockchains.dto.BlockchainDto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ru.itislabs.blockchains.dto.BlockchainDto();
  }

  public static ru.itislabs.blockchains.dto.BlockchainDto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BlockchainDto>
      PARSER = new com.google.protobuf.AbstractParser<BlockchainDto>() {
    @java.lang.Override
    public BlockchainDto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<BlockchainDto> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BlockchainDto> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ru.itislabs.blockchains.dto.BlockchainDto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

