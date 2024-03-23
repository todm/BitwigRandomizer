package org.todm;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.DocumentState;
import com.bitwig.extension.controller.api.RemoteControl;
import com.bitwig.extension.controller.api.SettableRangedValue;
import com.bitwig.extension.controller.api.Signal;
import java.util.Random;

import com.bitwig.extension.controller.ControllerExtension;

public class BitwigRandomizerExtension extends ControllerExtension {
   private ControllerHost host;
   private Random rand = new Random();
   private SettableRangedValue deviationValueState;
   private Signal randomizeSignalState;
   private CursorRemoteControlsPage[] deviceRemotes = new CursorRemoteControlsPage[128];

   protected BitwigRandomizerExtension(final BitwigRandomizerExtensionDefinition definition,
         final ControllerHost host) {
      super(definition, host);
      this.host = host;
   }

   @Override
   public void init() {

      initDocumentState();
      initParameterController();

      randomizeSignalState.addSignalObserver(() -> {
         randomizeRemotes();
      });
   }

   private void initDocumentState() {
      DocumentState documentState = host.getDocumentState();
      deviationValueState = documentState.getNumberSetting("Deviation Amount", "Settings", 0, 100, 1, "%", 100);
      randomizeSignalState = documentState.getSignalSetting("Randomize", "Action", "Go");

      deviationValueState.markInterested();
   }

   private void initParameterController() {
      CursorTrack cursorTrack = host.createCursorTrack(0, 0);
      CursorDevice cursorDevice = cursorTrack.createCursorDevice();

      for (int i = 0; i < deviceRemotes.length; i++) {
         String bankName = "$random" + (i + 1);
         deviceRemotes[i] = cursorDevice.createCursorRemoteControlsPage("DeviceRemotes" + i, 8, bankName);

         for (int j = 0; j < deviceRemotes[i].getParameterCount(); j++) {
            deviceRemotes[i].getParameter(j).markInterested();
            deviceRemotes[i].getName().markInterested();
            deviceRemotes[i].getParameter(j).setIndication(true);
         }
      }
   }

   private void randomizeRemotes() {
      for (int i = 0; i < deviceRemotes.length; i++) {
         for (int j = 0; j < deviceRemotes[i].getParameterCount(); j++) {
            RemoteControl parameter = deviceRemotes[i].getParameter(j);
            double value = parameter.get();
            double nextValue = getNextValue(value, deviationValueState.get());
            parameter.set(nextValue);
         }
      }
   }

   private double getNextValue(double current, Double deviation) {
      double rnd = rand.nextDouble();
      if (deviation == 1.0)
         return rnd;

      rnd = (rnd * 2.0 - 1.0) * deviation;
      double next = current + rnd;
      if (next > 1.0)
         return 1.0;
      if (next < 0.0)
         return 0.0;
      return next;
   }

   @Override
   public void exit() {
      getHost().showPopupNotification("BitwigRandomizer Exited");
   }

   @Override
   public void flush() {
   }

}
