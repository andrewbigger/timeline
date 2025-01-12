package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.ui.domain.Sprint;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import com.biggerconcept.timeline.ui.domain.TimelineRelease;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Inserts a release outline into a report
 * 
 * @author Andrew Bigger
 */
public class ReleaseOutlineElement extends Element {
    /**
     * Default constructor
     */
    public ReleaseOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public ReleaseOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a release table into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Document openDocument = getState().getOpenDocument();
            Preferences prefs = openDocument.getPreferences();
            Timeline tl = Timeline.fromState(getState());
            Year viewYear = getState().getViewYear();
            
            ArrayList<TimelineRelease> releases = 
                    tl.getReleasesInYear(viewYear);
            
            for (TimelineRelease tr : releases) {
                UUID lastEpicID = tr.getRelease().lastEpicId();
                TimelineEpic lastEpic = tl.findEpic(lastEpicID);
                
                document.h3(
                        getState()
                            .bundle()
                            .getString("reports.elements.releaseOutline.release")
                                + " "
                                + tr.getRelease().getNumber()
                                + " - " 
                                + tr.getName()
                );
                
                if (lastEpic != null) {
                    Sprint scheduledLastSprint = lastEpic.getLastSprint();
                    
                    document.strong(
                            getState()
                                    .bundle()
                                    .getString(
                                            "reports.elements.releaseOutline.target"
                                    )
                    );
                    
                    document.p(
                            "Q" 
                            + tl.quarterForSprint(
                                    prefs, 
                                    viewYear, 
                                    scheduledLastSprint
                            )
                    );
                    
                    document.nl();
                }
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.releaseOutline.mission"
                                )
                );

                document.md(tr.getRelease().getDescription());
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.releaseOutline.scope"
                                )
                );
                document.ol(tr.releaseScope(openDocument));
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.releaseOutline.includedEpics"
                                )
                );
                
                document.table(
                        EpicsTableElement.headers(getState().bundle()),
                        EpicsTableElement.body(
                                prefs, 
                                tr.includedEpics(openDocument)
                        )
                );

                document.br();
            }

        } catch (CloneNotSupportedException ex) {
            // skip release documentation when unable to construct timeline
        }
        
    }
    
    /**
     * Element modifiable. Indicates whether to allow the presentation of a
     * editor dialog in the report builder.
     * 
     * This element is not modifiable, so no editor dialog will be called for.
     * 
     * @return false
     */
    public boolean modifiable() {
        return false;
    }

}
