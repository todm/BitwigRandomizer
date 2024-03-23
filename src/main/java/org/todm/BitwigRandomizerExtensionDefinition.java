package org.todm;

import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class BitwigRandomizerExtensionDefinition extends ControllerExtensionDefinition {
   private static final UUID DRIVER_ID = UUID.fromString("3d39a230-b4b1-466d-9bfc-fca9aae34e63");

   public BitwigRandomizerExtensionDefinition() {
   }

   @Override
   public String getName() {
      return "BitwigRandomizer";
   }

   @Override
   public String getAuthor() {
      return "todm";
   }

   @Override
   public String getVersion() {
      return "0.1";
   }

   @Override
   public UUID getId() {
      return DRIVER_ID;
   }

   @Override
   public String getHardwareVendor() {
      return "todm";
   }

   @Override
   public String getHardwareModel() {
      return "BitwigRandomizer";
   }

   @Override
   public int getRequiredAPIVersion() {
      return 18;
   }

   @Override
   public int getNumMidiInPorts() {
      return 0;
   }

   @Override
   public int getNumMidiOutPorts() {
      return 0;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list,
         final PlatformType platformType) {
   }

   @Override
   public BitwigRandomizerExtension createInstance(final ControllerHost host) {
      return new BitwigRandomizerExtension(this, host);
   }
}
