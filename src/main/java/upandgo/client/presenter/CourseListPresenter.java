package upandgo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.view.CourseListView;
import com.allen_sauer.gwt.log.client.Log;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-17
 * 
 *        A concrete presenter for {@link CourseListView}.
 * 
 */

// TODO: add History management and Event management

public class CourseListPresenter implements Presenter {

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;

	String faculty = "";

	public interface Display {
		<T extends HasDoubleClickHandlers & HasMouseOverHandlers> T getSelectedCoursesList();

		<T extends HasDoubleClickHandlers & HasMouseOverHandlers> T getNotSelectedCoursesList();

		HasChangeHandlers getFacultyDropList();

		void setSelectedCourses(List<CourseId> courses);

		void setNotSelectedCourses(List<CourseId> courses);
		
		void setFaculties(List<String> faculties);

		Optional<CourseId> getSelectedCourse();	// you can pass Optional.absent() if there is no course selected

		Optional<CourseId> getUnselectedCourse();	// you can pass Optional.absent() if there is no course unselected

		String getFaculty();	// pass empty string if there is no faculty chosen

		Widget asWidget();
	}

	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}

	@Override
	public void bind() {
		// TODO
	}

	@Override
	public void unbind() {
		// TODO
	}

	@Override
	public void go(Panel panel) {
		bind();
		
		panel.clear();
		panel.add(display.asWidget());

		fetchCourses();
	}

//	@Override
//	public void onSelectedCourseClicked(CourseId clickedCourse) {
//		rpcService.unselectCourse(clickedCourse, new AsyncCallback<Void>() {
//			@Override
//			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
//				Window.alert("Error while unselecting course.");
//				Log.error("Error while unselecting course.");
//			}
//
//			@Override
//			public void onSuccess(@SuppressWarnings("unused") Void result) {
//				CourseListPresenter.this.fetchCourses();
//				CourseListPresenter.this.eventBus.fireEvent(new UnselectCourseEvent(clickedCourse));
//			}
//		});
//	}
//
//	@Override
//	public void onNotSelectedCourseClicked(CourseId clickedCourse) {
//		rpcService.selectCourse(clickedCourse, new AsyncCallback<Void>() {
//			@Override
//			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
//				Window.alert("Error while selecting course.");
//				Log.error("Error while selecting course.");
//			}
//
//			@Override
//			public void onSuccess(@SuppressWarnings("unused") Void result) {
//				CourseListPresenter.this.fetchCourses();
//				CourseListPresenter.this.eventBus.fireEvent(new SelectCourseEvent(clickedCourse));
//			}
//		});
//	}
//
//	@Override
//	public void onCourseHighlighted(CourseId highlightedCourse) {
//		eventBus.fireEvent(new HighlightCourseEvent(highlightedCourse));
//	}

	void fetchCourses() {
		rpcService.getSelectedCourses(new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				display.setSelectedCourses(result);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching selected courses.");
				Log.error("Error fetching selected courses.");
			}
		});

		rpcService.getNotSelectedCourses(faculty, new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				display.setNotSelectedCourses(result);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching not selected courses.");
				Log.error("Error fetching not selected courses.");
			}
		});
	}

	void fetchFaculties() {
		rpcService.getFaculties(new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				display.setFaculties(result);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching faculties.");
				Log.error("Error fetching faculties.");
			}
		});
	}
	
//	@Override
//	public void onFacultySelected(String f) {
//		if (this.faculty.equals(f))
//			return;
//		this.faculty = f;
//		fetchCourses();
//	}

}
